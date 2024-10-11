
// Title: Main.java
// Author: Kevin Nard
// Starting point for the application

package lab3.gui;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Main container for UI elements
        Window window = new Window(Theme.WINDOW_TITLE);

        // Layout manager
        GridBagLayout bag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        // Main panel
        Canvas canvas = new Canvas(bag);
        window.add(canvas);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        ExamplePanel0 panel0 = new ExamplePanel0();
        canvas.add(panel0, constraints);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weightx = 2;
        constraints.weighty = 4;
        ExamplePanel1 panel1 = new ExamplePanel1();
        canvas.add(panel1, constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        ExamplePanel2 panel2 = new ExamplePanel2();
        canvas.add(panel2, constraints);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weighty = 2;
        ExamplePanel3 panel3 = new ExamplePanel3();
        canvas.add(panel3, constraints);

        ExamplePanel4 panel4 = new ExamplePanel4();
        canvas.add(panel4, constraints);

        // Synchronize UI configuration
        window.revalidateEverything();
    }
}
