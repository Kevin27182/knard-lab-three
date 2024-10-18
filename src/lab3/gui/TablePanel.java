
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

    private final DataFrame dataDisplay;
    private Consumer<ArrayList<String>> exportConsumer;

    // Construct a new TablePanel from data
    public TablePanel(DataFrame dataDisplay) {

        // Set data
        this.dataDisplay = dataDisplay;

        // Set background to transparent
        setOpaque(false);

        // Set the layout to BorderLayout
        var layout = new BorderLayout();
        setLayout(layout);

        // Add padding around child components
        var border = new EmptyBorder(Theme.DEFAULT_INSETS);
        setBorder(border);

        // Reset the table
        resetTable();
    }

    // Set the update consumer
    public void setExportConsumer(Consumer<ArrayList<String>> exportConsumer) {
        this.exportConsumer = exportConsumer;
        resetTable();
    }

    // Reset table
    public void resetTable() {

        // Remove children if any
        removeAll();

        // Add Table component from data and exportConsumer
        Table table = new Table(dataDisplay, exportConsumer);
        add(table);
    }
}
