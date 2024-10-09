
// Title: Window.java
// Author: Kevin Nard
// Main container for UI elements

package lab3.gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Palette palette = Palette.LIGHT;

    // Construct a new window
    public Window() {
        setTitle("New Main");
        setPreferredSize(new Dimension(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    // Construct a new window with a customized title
    public Window(String name) {
        this();
        setTitle(name);
    }

    // Update palette
    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    // Return palette
    public Palette getPalette() {
        return palette;
    }
}
