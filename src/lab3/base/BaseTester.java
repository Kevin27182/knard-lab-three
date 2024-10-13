package lab3.base;

import java.io.IOException;

import static java.lang.System.exit;

public class BaseTester {

    public static void main(String[] args) {

        DataFrame myFrame = null;

        // Try to create a new data frame from `wine.csv`
        try {
            myFrame = new DataFrame("data/wine.csv", true);
        }
        catch (IOException e) {
            System.out.println("[ERROR] File not found: " + e.getMessage());
            exit(1);
        }

        // Print a glimpse of the data frame
        System.out.println("\nGlimpse of wine data: \n" + myFrame.glimpse() + "\n");

        // Print the number of rows
        System.out.println("Number of rows: \n" + myFrame.getNumberOfRows() + "\n");

        // Print the number of columns
        System.out.println("Number of columns: \n" + myFrame.getNumberOfColumns() + "\n");

        // Print the alcohol column
        System.out.println("Array of alcohol column: \n" + myFrame.getColumn("alcohol") + "\n");

        // Print the column at index 2
        System.out.println("Array of column at index 2: \n" + myFrame.getColumnAtIndex(2) + "\n");

        // Print the row at index 2
        System.out.println("Array of row at index 2: \n" + myFrame.getRowAtIndex(2) + "\n");

        // Print the output when a column can't be found
        System.out.println("Output of nonexistent column: \n" + myFrame.getColumn("something") + "\n");

        // Print the output when a column can't be found by index
        System.out.println("Output of nonexistent column at index out of range: \n" + myFrame.getColumnAtIndex(99) + "\n");

        // Print the output when a column can't be found by index
        System.out.println("Output of nonexistent row at index out of range: \n" + myFrame.getRowAtIndex(99) + "\n");

        // Print a glimpse of the data frame sorted by alcohol
        DataFrame sortedFrame = myFrame.sortByColumn("alcohol");
        System.out.println("Glimpse of wine data sorted by alcohol: \n" + sortedFrame.glimpse() + "\n");

        // Print a glimpse of the data frame sorted by alcohol, descending
        DataFrame reverseSortedFrame = myFrame.sortByColumn("alcohol", false);
        System.out.println("Glimpse of wine data sorted by alcohol, descending: \n" + reverseSortedFrame.glimpse() + "\n");

        // Show that original DataFrame is unchanged
        System.out.println("\nOriginal frame is unchanged: \n" + myFrame.glimpse() + "\n");
    }
}
