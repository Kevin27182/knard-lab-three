
// Title: TablePanel.java
// Author: Kevin Nard
// Panel that shows a table

package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class TablePanel extends JPanel {

    private Consumer<ArrayList<String>> exportConsumer;
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
        resetTable(dataDisplay);
    }

    // Set the update consumer
    public void setExportConsumer(Consumer<ArrayList<String>> exportConsumer, DataFrame dataDisplay) {
        this.exportConsumer = exportConsumer;
        resetTable(dataDisplay);
    }

    // Reset table
    public void resetTable(DataFrame dataDisplay) {

        String selectedColumn = null;

        // Get the selected cell if table not null
        if (table != null)
            selectedColumn = table.getSelectedColumn();

        // Remove children if any
        removeAll();

        // Add new Table component from data and exportConsumer
        table = new Table(dataDisplay, exportConsumer);
        add(table);

        // Export the current selected cell info if not null
        if (selectedColumn != null)
            table.getCellInfo(table.getColumn(selectedColumn));

        // Re-draw the table
        revalidate();
        repaint();
    }
}
