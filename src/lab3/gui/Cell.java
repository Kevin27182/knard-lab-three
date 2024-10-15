package lab3.gui;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {

    JLabel label = new JLabel();

    // Construct an empty cell
    public Cell() {
        label.setText("");
    }

    // Construct a cell with data -- default colors
    public Cell(String value) {
        this(value, Color.WHITE, Color.BLACK);
    }

    // Construct a cell with data
    public Cell(String value, Color bg, Color fg) {

        // Configure and add label
        label.setForeground(fg);
        label.setText(value);
        add(label);

        // Set the background and border colors
        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(Theme.DARK_BACKGROUND_2));
    }
}
