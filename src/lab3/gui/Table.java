package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Table extends JScrollPane {

    private final DataFrame sourceData;
    private ArrayList<Cell> headerCells;
    private ArrayList<ArrayList<Cell>> dataCells;
    private static final int SCROLL_SPEED = 10;

    // Construct a new empty table
    public Table() {
        sourceData = new DataFrame();
    }

    // Construct a new table from DataFrame input
    public Table(DataFrame data) {

        getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);

        this.sourceData = data;
        render(new DataFrame(this.sourceData));
    }

    // Create headerCells from sourceData header
    private void createHeader(DataFrame newData) {

        // Extract header from sourceData and convert to list of cells
        var headerExtract = newData.getHeader();
        var headerList = headerExtract.stream()
                .map(e -> new Cell(e, Theme.DARK_BACKGROUND_3, Theme.LIGHT_BACKGROUND_4))
                .toList();

        headerCells = new ArrayList<>(headerList);
    }

    // Create dataCells from sourceData
    private void createData(DataFrame newData) {

        // Extract sourceData and convert to 2D list of cells
        var dataExtract = newData.getData();
        var dataList = dataExtract.stream()

                /* -------------------------------------------------------------------+
                |  dataList has the type ArrayList<ArrayList<String>>                 |
                |  in other words, an ArrayList of ArrayLists                         |
                |  the first map is for the outer ArrayLists containing ArrayLists    |
                |  the second map is for the inner ArrayLists containing String sourceData  |
                +--------------------------------------------------------------------*/
                .map(e -> new ArrayList<>(e.stream()
                        .map(val -> new Cell(val, Theme.LIGHT_BACKGROUND_1, Theme.TEXT))
                        .toList()))
                .toList();

        dataCells = new ArrayList<>(dataList);
    }

    // Render the table
    private void render(DataFrame newData) {

        // Inner class that holds a grid -- serves as the JScrollPane view
        class RenderPanel extends JPanel {

            // Construct a new RenderPanel
            public RenderPanel(int numRows, int numCols) {
                var newGridLayout = new GridLayout(numRows, numCols);
                setLayout(newGridLayout);
            }
        }

        // Create header and cell sourceData from DataFrame
        createHeader(newData);
        createData(newData);

        // Define the dimensions of the grid
        int numRows = 1 + newData.getNumberOfRows();
        int numCols = newData.getNumberOfColumns();

        // Create a RenderPanel, add the sourceData, then set to viewport view
        RenderPanel renderPanel = new RenderPanel(numRows, numCols);
        headerCells.forEach(renderPanel::add);
        dataCells.forEach(e -> e.forEach(renderPanel::add));
        setViewportView(renderPanel);

        // Redraw the table
        revalidate();
        repaint();
    }
}
