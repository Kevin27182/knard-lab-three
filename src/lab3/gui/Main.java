
// Title: Main.java
// Author: Kevin Nard
// Starting point for the application

package lab3.gui;

import lab3.base.DataFrame;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {

        DataFrame data = null;
        DataFrame dataDisplay = null;

        // Try to create a new data frame from `wine.csv`
        try {
            data = DataFrame.readCSV("data/wine.csv", true);
            dataDisplay = new DataFrame(data);
        }
        catch (IOException e) {
            System.out.println("[ERROR] File not found: " + e.getMessage());
            exit(1);
        }

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
        canvas.add(new ControlPanel(data, dataDisplay), constraints);

        // Configure constraints and add chart panel
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weightx = 2;
        constraints.weighty = 3;
        canvas.add(new ChartPanel(data, dataDisplay), constraints);

        // Configure constraints and add stats panel
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        canvas.add(new StatsPanel(data, dataDisplay), constraints);

        // Configure constraints and add table panel
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weighty = 2;
        TablePanel tablePanel = new TablePanel(data, dataDisplay);
        canvas.add(tablePanel, constraints);

        // Add details panel
        DetailsPanel detailsPanel = new DetailsPanel();
        canvas.add(detailsPanel, constraints);

        // Consumer for sending cell info to details panel
        Consumer<ArrayList<String>> detailsConsumer = e -> {
            detailsPanel.displayDetails(e);
            System.out.println("Do something in DetailsPanel");
            System.out.println(e);
        };
        tablePanel.setExportConsumer(detailsConsumer);

        // Synchronize UI configuration
        window.revalidateEverything();
    }
}
