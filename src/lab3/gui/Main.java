
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
        ControlPanel controlPanel = new ControlPanel();
        canvas.add(controlPanel, constraints);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weightx = 2;
        constraints.weighty = 3;
        ChartPanel chartPanel = new ChartPanel();
        canvas.add(chartPanel, constraints);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        StatsPanel statsPanel = new StatsPanel();
        canvas.add(statsPanel, constraints);

        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weighty = 2;
        TablePanel tablePanel = new TablePanel();
        canvas.add(tablePanel, constraints);

        DetailsPanel detailsPanel = new DetailsPanel();
        canvas.add(detailsPanel, constraints);

        // Synchronize UI configuration
        window.revalidateEverything();
    }
}
