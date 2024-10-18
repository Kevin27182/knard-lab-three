
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

public class ChartPanel extends JPanel {

    private final DataFrame dataDisplay;

    public ChartPanel(DataFrame dataDisplay) {

        // Set `dataDisplay`
        this.dataDisplay = dataDisplay;

        // Set background to transparent
        setOpaque(false);

        // Add padding around child components
        var border = new EmptyBorder(Theme.DEFAULT_INSETS);
        setBorder(border);

        // Set Layout
        setLayout(new BorderLayout());

        // Create and add default init message
        add(new InitPanel("Click a column in the table to view its distribution."), BorderLayout.CENTER);
    }

    private static class InitPanel extends JPanel {
        InitPanel(String msg) {
            JLabel initLabel = new JLabel(msg);
            initLabel.setMinimumSize(new Dimension(0,0));
            initLabel.setPreferredSize(new Dimension(300,20));
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

    public void updateHistogram(String column, int numBins) {

        // Remove all children if any
        removeAll();

        // Create data list from `column`
        var list = dataDisplay.getColumn(column);

        // Create and add histogram
        Histogram hist = new Histogram(createChart(list, numBins, column));
        add(hist);

        // Update UI
        revalidate();
        repaint();
    }
}
