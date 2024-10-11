
// Title: Main.java
// Author: Kevin Nard
// Starting point for the application

package lab3.gui;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        // Main container for UI elements
        Window window = new Window(Theme.WINDOW_TITLE);

        // Layout manager and constraints
        GridBagLayout bag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        // Main panel
        Canvas canvas = new Canvas(bag);
        window.add(canvas);

        // Configure constraints and add control panel
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        ControlPanel controlPanel = new ControlPanel();
        canvas.add(controlPanel, constraints);

        // Configure constraints and add chart panel
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weightx = 2;
        constraints.weighty = 3;
        ChartPanel chartPanel = new ChartPanel();
        canvas.add(chartPanel, constraints);

        // Configure constraints and add stats panel
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        StatsPanel statsPanel = new StatsPanel();
        canvas.add(statsPanel, constraints);

        // Configure constraints and add table panel
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weighty = 2;
        TablePanel tablePanel = new TablePanel();
        canvas.add(tablePanel, constraints);

        // Add details panel
        DetailsPanel detailsPanel = new DetailsPanel();
        canvas.add(detailsPanel, constraints);

        // Synchronize UI configuration
        window.revalidateEverything();
    }
}
