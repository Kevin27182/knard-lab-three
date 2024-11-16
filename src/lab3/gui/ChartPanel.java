
// Title: ChartPanel.java
// Author: Kevin Nard
// Panel that shows a chart

package lab3.gui;

import lab3.base.DataFrame;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.statistics.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ChartPanel extends JPanel implements TableObserver {

    private final InitPanel startup = new InitPanel("Click a column in the table to view its distribution.");

    public ChartPanel() {

        // Set background to transparent
        setOpaque(false);

        // Add padding around child components
        var border = new EmptyBorder(Theme.DEFAULT_INSETS);
        setBorder(border);

        // Set Layout
        setLayout(new BorderLayout());

        // Create and add default init message
        add(startup, BorderLayout.CENTER);
    }

    @Override
    public void onTableUpdateHandler(TableObserverData data) {
        // Extract a string representation of the selected column
        var column = data.getDetails().stream().filter(item -> item.contains("Column: ")).findFirst();
        var stringRep = column.orElse("").replace("Column: ", "");

        // Render stats if a column is selected
        if (column.isPresent()) {
            updateHistogram(data.getDataDisplay(), stringRep, 20);
        }
    }

    private static class InitPanel extends JPanel {
        InitPanel(String msg) {
            JLabel initLabel = new JLabel(msg);
            initLabel.setMinimumSize(new Dimension(0,0));
            initLabel.setPreferredSize(new Dimension(300,20));
            setPreferredSize(new Dimension(0,0));
            add(initLabel);
            setBackground(Theme.LIGHT_BACKGROUND_1);
        }
    }

    // Wrapper class for JFreeChart's ChartPanel
    private static class Histogram extends org.jfree.chart.ChartPanel {
        public Histogram(JFreeChart chart) {
            super(chart);
        }
    }

    // Return a histogram chart
    private JFreeChart createChart(ArrayList<String> list, int numBins, String title) {

        // Convert `list` into a double[] array
        double[] seriesArray = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            seriesArray[i] = Double.parseDouble(list.get(i));
        }

        // Create dataset and add a series
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("", seriesArray, numBins);

        // Create and configure the histogram
        var hist = ChartFactory.createHistogram(title, title, "Frequency", dataset, PlotOrientation.VERTICAL, true, true, false);
        hist.removeLegend();

        return hist;
    }

    public void updateHistogram(DataFrame dataDisplay, String column, int numBins) {

        var comps = Arrays.asList(getComponents());
        InitPanel msg = new InitPanel("There's no data to display. Try adjusting your filters!");

        if (!comps.isEmpty()) {
            if (comps.getFirst() == startup)
                msg = startup;
        }

        // Remove all children if any
        removeAll();

        // Create data list from `column`
        var list = dataDisplay.getColumn(column);

        // Create and add histogram
        if (!list.isEmpty()) {
            Histogram hist = new Histogram(createChart(list, numBins, column));
            hist.setPreferredSize(new Dimension(10000,10000));
            add(hist);
        }

        // Show missing data message if previous was a histogram
        else
            add(msg);

        // Update UI
        revalidate();
        repaint();
    }
}
