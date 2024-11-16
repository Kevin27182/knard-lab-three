
// Title: TablePanel.java
// Author: Kevin Nard
// Panel that shows a table

package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TablePanel extends JPanel {

    private BiConsumer<Integer, Boolean> sortDataConsumer;
    private BiConsumer<Integer, Boolean> sortDataDisplayConsumer;
    private Table table;
    private final ArrayList<TableObserver> tableObservers = new ArrayList<>();
    private Consumer<ArrayList<String>> updateConsumer;

    // Construct a new TablePanel from data
    public TablePanel(DataFrame dataDisplay) {

        // Set background to transparent
        setOpaque(false);

        // Set the layout to BorderLayout
        var layout = new BorderLayout();
        setLayout(layout);

        // Add padding around child components
        var border = new EmptyBorder(Theme.DEFAULT_INSETS);
        setBorder(border);

        // Reset the table
        resetTable(dataDisplay, getSortedAscending());
    }

    // Set the data sort consumer
    public void setSortDataConsumer(BiConsumer<Integer, Boolean> sortDataConsumer, DataFrame dataDisplay) {
        this.sortDataConsumer = sortDataConsumer;
        resetTable(dataDisplay, getSortedAscending());
    }

    // Set the dataDisplay sort consumer
    public void setSortDataDisplayConsumer(BiConsumer<Integer, Boolean> sortDataDisplayConsumer, DataFrame dataDisplay) {
        this.sortDataDisplayConsumer = sortDataDisplayConsumer;
        resetTable(dataDisplay, getSortedAscending());
    }

    // Reset table
    public void resetTable(DataFrame dataDisplay, boolean sortedAscending) {

        String selectedColumn = null;
        int sortColumnIndex = -1;

        // Update consumer for observers
        setUpdateConsumer(dataDisplay);

        // Get the selected cell if table not null
        if (table != null) {
            selectedColumn = table.getSelectedColumn();
            sortColumnIndex = table.getSortColumnIndex();
        }

        // Remove children if any
        removeAll();

        // Add new Table component from data and exportConsumer
        table = new Table(dataDisplay, sortDataConsumer, sortDataDisplayConsumer, updateConsumer, sortedAscending, sortColumnIndex);
        add(table);

        // Export the current selected cell info if not null
        if (selectedColumn != null)
            table.getCellInfo(table.getColumn(selectedColumn), dataDisplay, true);

        // Re-draw the table
        revalidate();
        repaint();
    }

    // Return `sortedAscending`
    public boolean getSortedAscending() {

        // If table is null, return false
        if (table == null)
            return false;

        return table.getSortedAscending();
    }

    // Return the selected column
    public String getSortColumn() {
        return table.getSelectedColumn();
    }

    // Add a table observer
    public void addTableObserver(TableObserver observer) {
        tableObservers.add(observer);
    }

    // Update the TableObservers
    public void updateTableObservers(DataFrame dataDisplay, ArrayList<String> details) {

        TableObserverData data = new TableObserverData(dataDisplay, details);

        for (TableObserver observer : tableObservers)
            observer.onTableUpdateHandler(data);
    }

    // Update the updateConsumer
    public void setUpdateConsumer(DataFrame dataDisplay) {
        this.updateConsumer = details -> updateTableObservers(dataDisplay, details);
    }
}
