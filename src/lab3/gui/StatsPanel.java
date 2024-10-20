
// Title: StatsPanel.java
// Author: Kevin Nard
// Panel that shows statistics

package lab3.gui;

import lab3.base.DataFrame;
import java.awt.*;
import java.util.ArrayList;

public class StatsPanel extends DetailsPanel {


    public StatsPanel(String title, Color color) {
        super(title, color);
    }

    public void displayDetails(DataFrame dataDisplay, String column) {

        // Get column from parameter
        var col = dataDisplay.getColumn(column);

        if (col.isEmpty()) {
            super.displayDetails(new ArrayList<>());
            return;
        }

        // Convert `details` to numeric array
        var numericStream = col.stream().map(Double::parseDouble);
        var numericList = new ArrayList<>(numericStream.toList());

        int digits = 4;

        // Construct numeric details with common statistics
        var numericDetails = new ArrayList<String>();
        numericDetails.add("Sum: " + round(sum(numericList)));
        numericDetails.add("Mean: " + round(mean(numericList)));
        numericDetails.add("Variance: " + round(var(numericList), digits));
        numericDetails.add("Standard Deviation: " + round(sd(numericList), digits));
        numericDetails.add("Min: " + round(min(numericList)));
        numericDetails.add("Max: " + round(max(numericList)));
        numericDetails.add("Range: " + round(range(numericList)));

        // Display numeric details
        super.displayDetails(numericDetails);
    }

    // Round a number to the specified number of digits
    private double round(double num, int digits) {
        long rounded = Math.round(num * Math.pow(10, digits));
        return rounded / Math.pow(10, digits);
    }

    // Round a number to the specified number of digits, default 2 digits
    private double round(double num) {
        return round(num, 2);
    }

    // Compute the sum of the `numericList`
    private double sum(ArrayList<Double> numericList) {
        double sum = 0;
        for (Double num : numericList) {
            sum += num;
        }
        return sum;
    }

    // Compute the mean of the `numericList`
    private double mean(ArrayList<Double> numericList) {
        return sum(numericList) / numericList.size();
    }

    // Compute the variance of the `numericList`
    private double var(ArrayList<Double> numericList) {
        double mean = mean(numericList);
        double sum = 0;
        for (Double num : numericList) {
            sum += Math.pow(num - mean, 2);
        }
        return sum / (numericList.size() - 1);
    }

    // Compute the standard deviation of the `numericList`
    private double sd(ArrayList<Double> numericList) {
        return Math.sqrt(var(numericList));
    }

    // Compute the min of the `numericList`
    private double min(ArrayList<Double> numericList) {
        return numericList.stream().min(Double::compare).orElseThrow();
    }

    // Compute the max of the `numericList`
    private double max(ArrayList<Double> numericList) {
        return numericList.stream().max(Double::compare).orElseThrow();
    }

    // Compute the range of the `numericList`
    private double range(ArrayList<Double> numericList) {
        return max(numericList) - min(numericList);
    }
}
