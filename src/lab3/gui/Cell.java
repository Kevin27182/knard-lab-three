
// Title: Cell.java
// Author: Kevin Nard
// Holds a visual representation of a piece of data

package lab3.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class Cell extends JPanel {

    JLabel label = new JLabel();

    // Construct an empty cell
    public Cell() {
        label.setText("");
    }

    // Construct a cell with data -- default colors
    public Cell(String value, Consumer<Cell> cellAction) {
        this(value, Color.WHITE, Color.BLACK, cellAction);
    }

    // Construct a cell with data
    public Cell(String value, Color bg, Color fg, Consumer<Cell> cellAction) {

        // Configure and add label
        label.setForeground(fg);
        label.setText(value);
        add(label);

        // Set the background and border colors
        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(Theme.DARK_BACKGROUND_2));

        // Mouse Listener for interactivity
        this.addMouseListener(new MouseAdapter() {

            // Highlight the cell on mouse hover
            public void mouseEntered(MouseEvent e) {
                setBackground(Theme.BLUE_3);
            }

            // Reset cell highlight on mouse exit
            public void mouseExited(MouseEvent e) {
                setBackground(bg);
            }

            // Send self reference to consumer
            public void mouseClicked(MouseEvent e) {
                cellAction.accept(Cell.this);
            }
        });
    }

    // Return the cell's value
    public String getValue() {
        return label.getText();
    }
}
