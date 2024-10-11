
// Title: DataFrame.java
// Author: Kevin Nard
// Data structure that holds tabular data

package lab3.base;

import java.util.ArrayList;

public class DataFrame {

    private ArrayList<String> header;
    private ArrayList<ArrayList<?>> data;

    // Construct a new data frame
    public DataFrame(String path) {
        this(path, true);
    }

    // Construct a new data frame, header optional
    public DataFrame(String path, boolean header) {
        readCSV(path, header);
    }

    // Read in data from a csv file
    private void readCSV(String path) {
        readCSV(path, true);
    }

    // Read in data from a csv file, header optional
    private void readCSV(String path, boolean header) {
        // Code that reads a csv and fills ArrayList fields with data
    }
}
