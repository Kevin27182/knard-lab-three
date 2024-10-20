package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static lab3.base.Validation.isNumeric;

public class Table extends JScrollPane {

    private final Consumer<ArrayList<String>> exportConsumer;
    private final BiConsumer<Integer, Boolean> sortDataConsumer;
    private final BiConsumer<Integer, Boolean> sortDataDisplayConsumer;
    private ArrayList<Cell> headerCells;
    private ArrayList<ArrayList<Cell>> dataCells;
    private static final int SCROLL_SPEED = 10;
    private int sortColumnIndex;
    private boolean sortedAscending;
    private String selectedColumn;

    // Construct a new table from DataFrame input
    public Table(
            DataFrame dataDisplay,
            Consumer<ArrayList<String>> exportConsumer,
            BiConsumer<Integer, Boolean> sortDataConsumer,
            BiConsumer<Integer, Boolean> sortDataDisplayConsumer,
            boolean sortedAscending,
            int sortColumnIndex
    ) {

        this.exportConsumer = exportConsumer;
        this.sortDataConsumer = sortDataConsumer;
        this.sortDataDisplayConsumer = sortDataDisplayConsumer;
        this.sortColumnIndex = sortColumnIndex;
        this.sortedAscending = sortedAscending;
        getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        render(dataDisplay);
    }

    // Create headerCells from sourceData header
    private void createHeader(DataFrame dataDisplay) {

        // Extract header from sourceData and convert to list of cells
        var headerExtract = dataDisplay.getHeader();
        var headerList = headerExtract.stream()
                .map(value -> new Cell(value, Theme.DARK_BACKGROUND_3, Theme.LIGHT_BACKGROUND_4, cell -> getCellInfo(cell, dataDisplay)))
                .toList();

        headerCells = new ArrayList<>(headerList);
    }

    // Create dataCells from sourceData
    private void createData(DataFrame dataDisplay) {

        // Extract sourceData and convert to 2D list of cells
        var dataExtract = dataDisplay.getData();
        var dataList = dataExtract.stream()

                /* -------------------------------------------------------------------+
                |  dataList has the type ArrayList<ArrayList<String>>                 |
                |  in other words, an ArrayList of ArrayLists                         |
                |  the first map is for the outer ArrayLists containing ArrayLists    |
                |  the second map is for the inner ArrayLists containing String sourceData  |
                +--------------------------------------------------------------------*/
                .map(e -> new ArrayList<>(e.stream()
                        .map(value -> new Cell(value, Theme.LIGHT_BACKGROUND_1, Theme.TEXT, cell -> getCellInfo(cell, dataDisplay)))
                        .toList()))
                .toList();

        dataCells = new ArrayList<>(dataList);
    }

    // Render the table
    private void render(DataFrame dataDisplay) {

        // Inner class that holds a grid -- serves as the JScrollPane view
        class RenderPanel extends JPanel {

            // Construct a new RenderPanel
            public RenderPanel(int numRows, int numCols) {
                var newGridLayout = new GridLayout(numRows, numCols);
                setLayout(newGridLayout);
            }
        }

        // Create header and cell sourceData from DataFrame
        createHeader(dataDisplay);
        createData(dataDisplay);

        // Define the dimensions of the grid
        int numRows = 1 + dataDisplay.getNumberOfRows();
        int numCols = dataDisplay.getNumberOfColumns();

        // Create a RenderPanel, add the sourceData, then set to viewport view
        RenderPanel renderPanel = new RenderPanel(numRows, numCols);
        headerCells.forEach(renderPanel::add);
        dataCells.forEach(e -> e.forEach(renderPanel::add));
        setViewportView(renderPanel);

        // Redraw the table
        revalidate();
        repaint();
    }

    // Get cell information, sorting toggle on by default
    public void getCellInfo(Cell cell, DataFrame dataDisplay) {
        this.getCellInfo(cell, dataDisplay, false);
    }

    // Get cell information
    public void getCellInfo(Cell cell, DataFrame dataDisplay, boolean skipSorting) {

        // Create new 2D ArrayList with header and dataDisplay
        var concat = new ArrayList<>(dataCells);
        concat.addFirst(headerCells);

        // Find the row containing `cell` and get indexes
        var cellRow = concat.parallelStream().filter(row -> row.contains(cell)).toList().getFirst();
        int cellRowIndex = concat.indexOf(cellRow);
        int cellColumnIndex = cellRow.indexOf(cell);

        // Get the name of the column containing `cell`
        String columnName = headerCells.get(cellColumnIndex).getValue();

        // Test whether `cell` is numeric or a string
        boolean cellNumeric = isNumeric(cell.getValue());

        // Stores cell information
        ArrayList<String> cellInfo = new ArrayList<>();

        // Process dataDisplay cells
        if (cellRowIndex > 0) {
            cellInfo.add("Value: " + cell.getValue());
            cellInfo.add("Variable: " + columnName);
            cellInfo.add("Type: ".concat(cellNumeric ? "Numeric" : "String"));
            cellInfo.add("Index: (" + cellRowIndex + ", " + cellColumnIndex + ")");
            exportConsumer.accept(cellInfo);
        }

        // Process header cells
        else {

            // Toggle sort direction if the same column has been selected
            if (!skipSorting)
                sortedAscending = !sortedAscending;

            // Default to ascending sort if a new column is selected
            if (!skipSorting && cellColumnIndex != sortColumnIndex)
                sortedAscending = true;

            dataDisplay = sortTable(cellColumnIndex, dataDisplay, sortedAscending);
            cellInfo.add("Column: " + columnName);
            cellInfo.add("Size: " + dataDisplay.getColumnAtIndex(cellColumnIndex).size());
            cellInfo.add("Sorted: ".concat(sortedAscending ? "Ascending" : "Descending"));
            exportConsumer.accept(cellInfo);
        }

        // Set current selection
        selectedColumn = columnName;

        // Render the new data display
        render(dataDisplay);
    }

    // Given a column name, return the header cell for that column
    public Cell getColumn(String column) {

        return headerCells.stream()

                // Filter stream to cells that contain `column`
                .filter(contents -> contents.getValue().equals(column))

                // Reduce the stream to an optional
                .findFirst()

                // Reduce to cell or throw an error if empty
                .orElse(null);
    }

    // Return the selected column
    public String getSelectedColumn() {
        return selectedColumn;
    }

    // Return `sortedAscending`
    public boolean getSortedAscending() {
        return sortedAscending;
    }

    // Return sort index
    public int getSortColumnIndex() {
        return sortColumnIndex;
    }

    // Sort the table
    private DataFrame sortTable(int cellColumnIndex, DataFrame dataDisplay, boolean sortedAscending) {

        // Set fields for new sort column
        if (cellColumnIndex != sortColumnIndex) {
            sortedAscending = true;
            sortColumnIndex = cellColumnIndex;
        }

        // Sort the dataDisplay
        if (dataDisplay.getNumberOfRows() > 0)
            sortDataDisplayConsumer.accept(cellColumnIndex, sortedAscending);

        // Sort parent data
        sortDataConsumer.accept(cellColumnIndex, sortedAscending);

        if (dataDisplay.getNumberOfRows() > 0)
            return dataDisplay.sortByColumnIndex(cellColumnIndex, sortedAscending);

        return dataDisplay;
    }
}
