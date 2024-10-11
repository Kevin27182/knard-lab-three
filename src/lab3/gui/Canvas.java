
// Title: Window.java
// Author: Kevin Nard
// Main panel for defining layout and container for other panels

package lab3.gui;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    // Construct a new canvas
    public Canvas(GridBagLayout bag) {
        setBackground(Theme.DARK_BACKGROUND_1);
        setLayout(bag);
    }
}
