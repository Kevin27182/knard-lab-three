
// Title: Main.java
// Author: Kevin Nard
// Starting point for the application

package lab3.gui;

import lab3.base.DataFrame;
import java.awt.*;
import java.io.IOException;
import static java.lang.System.exit;

public class Main {

    private static DataFrame data;
    private static DataFrame dataDisplay;

    public static void main(String[] args) {

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
        constraints.weighty = 0.1;
        ControlPanel controlPanel = new ControlPanel(data,() -> data);
        canvas.add(controlPanel, constraints);

        // Configure constraints and add chart panel
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weightx = 2;
        constraints.weighty = 3;
        ChartPanel chartPanel = new ChartPanel(dataDisplay);
        canvas.add(chartPanel, constraints);

        // Configure constraints and add stats panel
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        StatsPanel statsPanel = new StatsPanel(dataDisplay, "Statistics", Theme.LIME);
        canvas.add(statsPanel, constraints);

        // Configure constraints and add table panel
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.weighty = 2;
        TablePanel tablePanel = new TablePanel(dataDisplay);
        canvas.add(tablePanel, constraints);

        // Add details panel
        DetailsPanel detailsPanel = new DetailsPanel("Details", Theme.BLUE_3);
        canvas.add(detailsPanel, constraints);

        // BiConsumers for sorting
        tablePanel.setSortDataConsumer((index, ascending) -> data = data.sortByColumnIndex(index, ascending), dataDisplay);
        tablePanel.setSortDataDisplayConsumer((index, ascending) -> dataDisplay = dataDisplay.sortByColumnIndex(index, ascending), dataDisplay);

        // Consumer for exporting info from table panel
        tablePanel.setExportConsumer(export -> {

            // Send exported cell info to the details panel
            detailsPanel.displayDetails(export);

            // Extract a string representation of the selected column
            var column = export.stream().filter(item -> item.contains("Column: ")).findFirst();
            var stringRep = column.orElse("").replace("Column: ", "");

            // Render a histogram of selected column
            if (column.isPresent()) {
                chartPanel.updateHistogram(stringRep, 20);
                statsPanel.displayDetails(stringRep);
            }

        }, dataDisplay);

        // Runnable for updating the UI after filtering
        controlPanel.setUpdateUI(df -> {
            //System.out.println(tablePanel.getSortedAscending());
            tablePanel.resetTable(df, tablePanel.getSortedAscending());
        });

        // Consumer for updating `dataDisplay`
        controlPanel.setUpdateDataDisplay(df -> dataDisplay = df);

        // Synchronize UI configuration
        window.revalidateEverything();
    }
}
