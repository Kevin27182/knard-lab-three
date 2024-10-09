
// Title: Window.java
// Author: Kevin Nard
// Main container for UI elements

package lab3.gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Theme theme = Theme.LIGHT;

    // Construct a new window
    public Window() {
        setTitle("New Main");
        setPreferredSize(new Dimension(Aesthetics.WINDOW_WIDTH, Aesthetics.WINDOW_HEIGHT));
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

    // Update theme
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    // Return theme
    public Theme getTheme() {
        return theme;
    }
}
