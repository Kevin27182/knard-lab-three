
// Title: TablePanel.java
// Author: Kevin Nard
// Panel that shows a table

package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TablePanel extends JPanel {

    private static final int PADDING = 10;

    // Construct a new TablePanel from data
    public TablePanel(DataFrame data) {

        // Set background to transparent
        setOpaque(false);

        // Set the layout to BorderLayout
        var layout = new BorderLayout();
        setLayout(layout);

        // Add padding around child components
        var border = new EmptyBorder(PADDING, PADDING, PADDING, PADDING);
        setBorder(border);

        // Add Table component from data
        add(new Table(data));
    }
}
