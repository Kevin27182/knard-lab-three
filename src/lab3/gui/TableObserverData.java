
// Title: StatsPanel.java
// Author: Kevin Nard
// Data structure for sending information to TableObservers

package lab3.gui;

import lab3.base.DataFrame;
import java.util.ArrayList;

public class TableObserverData {

    private final DataFrame dataDisplay;
    private final ArrayList<String> details;

    // Initialize the data structure
    public TableObserverData(DataFrame dataDisplay, ArrayList<String> details) {
        this.dataDisplay = dataDisplay;
        this.details = details;
    }

    // Return dataDisplay
    public DataFrame getDataDisplay() {
        return dataDisplay;
    }

    // Return details
    public ArrayList<String> getDetails() {
        return details;
    }
}
