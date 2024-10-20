
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

    private Consumer<ArrayList<String>> exportConsumer;
    private BiConsumer<Integer, Boolean> sortDataConsumer;
    private BiConsumer<Integer, Boolean> sortDataDisplayConsumer;
    private Table table;

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

    // Set the update consumer
    public void setExportConsumer(Consumer<ArrayList<String>> exportConsumer, DataFrame dataDisplay) {
        this.exportConsumer = exportConsumer;
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

        // Get the selected cell if table not null
        if (table != null) {
            selectedColumn = table.getSelectedColumn();
            sortColumnIndex = table.getSortColumnIndex();
        }

        // Remove children if any
        removeAll();

        // Add new Table component from data and exportConsumer
        table = new Table(dataDisplay, exportConsumer, sortDataConsumer, sortDataDisplayConsumer, sortedAscending, sortColumnIndex);
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
}
