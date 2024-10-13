
// Title: DataFrame.java
// Author: Kevin Nard
// Data structure that holds tabular data

package lab3.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DataFrame {

    private ArrayList<String> header;
    private ArrayList<ArrayList<String>> data = new ArrayList<>();
    private final String CSV_REGEX = "[,\n]";
    private final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";


    // Construct a new data frame from data and header parameters
    private DataFrame(ArrayList<ArrayList<String>> data, ArrayList<String> header) {
        this.data = data;
        this.header = header;
    }

    // Construct a new data frame from data parameter, header null by default
    private DataFrame(ArrayList<ArrayList<String>> data) {
        this(data, null);
    }

    // Construct a new data frame
    public DataFrame(String path) throws IOException {
        this(path, true);
    }

    // Construct a new data frame, header optional
    public DataFrame(String path, boolean header) throws IOException {
        readCSV(path, header);
    }

    // Construct a new DataFrame from an existing Data Frame
    public DataFrame(DataFrame frame) {
        this.header = new ArrayList<>(frame.getHeader());
        this.data = new ArrayList<>(frame.getData());
    }

    // Return the header
    private ArrayList<String> getHeader() {
        return header;
    }

    // Return the data
    private ArrayList<ArrayList<String>> getData() {
        return data;
    }

    // Read in data from a csv file
    private void readCSV(String path) throws IOException {
        readCSV(path, true);
    }

    // Read in data from a csv file, header optional
    private void readCSV(String path, boolean header) throws IOException {

        // Get the header from the CSV at `path`
        if (header)
            this.header = getHeaderFromFile(path);

        // Get the data from the CSV at `path`
        this.data = getDataFromFile(path, header);
    }

    // Get the header from CSV at `path`
    private ArrayList<String> getHeaderFromFile(String path) throws IOException {

        Path pathObj = Path.of(path);
        var header = new ArrayList<String>();

        // Create a new stream from CSV at `path`
        try (Stream<String> lines = Files.lines(pathObj)) {
            // Process only the first line
            lines.limit(1).forEach(e -> {
                // Split the line by delimiters and add each value to header
                Pattern.compile(CSV_REGEX).splitAsStream(e).forEach(header::add);
            });
        }

        return header;
    }

    // Get the data from CSV at `path`
    private ArrayList<ArrayList<String>> getDataFromFile(String path, boolean header) throws IOException {

        Path pathObj = Path.of(path);
        var data = new ArrayList<ArrayList<String>>();

        try (Stream<String> lines = Files.lines(pathObj)) {

            Stream<String> dataStream = lines;
            if (header)
                dataStream = dataStream.skip(1);

            dataStream.forEach(line -> {
                var lineList = Pattern.compile(CSV_REGEX).splitAsStream(line).toList();
                data.add(new ArrayList<>(lineList));
            });
        }

        return data;
    }

    // Return a string showing a glimpse of the data in the DataFrame
    public String glimpse() {

        String contents = "";
        Stream<ArrayList<String>> startDataStream = data.stream().limit(3);
        Stream<ArrayList<String>> endDataStream = data.stream().skip(data.size() - 3);

        // Add the header to `contents` if it exists
        if (header != null) {
            contents += header + "\n";
        }

        // Add the first three rows to `contents`
        var startList = startDataStream.toList();
        for (ArrayList<String> strings : startList)
            contents = contents.concat(strings + "\n");

        contents += "...\n";

        // Add the last three rows to `contents`
        var endList = endDataStream.toList();
        for (ArrayList<String> strings : endList)
            contents = contents.concat(strings + "\n");

        // Remove the final newline character and return contents
        return contents.substring(0, contents.length() - 1);
    }

    // Return an ArrayList representation of a column, access by name
    public ArrayList<String> getColumn(String column) {

        // Return an empty ArrayList if there is no header
        if (header == null)
            return new ArrayList<>();

        int columnIndex = header.indexOf(column);
        return getColumnAtIndex(columnIndex);
    }

    // Return an ArrayList representation of a column, access by index
    public ArrayList<String> getColumnAtIndex(int columnIndex) {

        // If the column index is out of range, return an empty ArrayList
        if (columnIndex < 0 || columnIndex >= getNumberOfColumns())
            return new ArrayList<>();

        // Extract a List of the elements corresponding to `column`
        var columnList = data.stream().map(e -> e.get(columnIndex)).toList();

        // Cast to ArrayList<> and return
        return new ArrayList<>(columnList);
    }

    // Return an ArrayList representation of a row, access by index
    public ArrayList<String> getRowAtIndex(int rowIndex) {

        // If the row index is out of range, return an empty ArrayList
        if (rowIndex < 0 || rowIndex >= getNumberOfRows())
            return new ArrayList<>();

        // Extract a List of the elements at `rowIndex`
        var rowList = data.get(rowIndex);

        // Cast to ArrayList<> and return
        return new ArrayList<>(rowList);
    }

    // Return the number of rows
    public int getNumberOfRows() {
        return data.size();
    }

    // Return the number of columns
    public int getNumberOfColumns() {
        return data.getFirst().size();
    }

    // Return a new DataFrame sorted by `column`, access by name
    public DataFrame sortByColumn(String column, boolean ascending) {

        // If header does not exist, throw an exception
        if (header == null)
            throw new RuntimeException("[ERROR] Header not present: cannot access by column name.");

        // Get index of column
        int columnIndex = header.indexOf(column);

        return sortByColumnIndex(columnIndex, ascending);
    }

    // Override: ascending = true by default
    public DataFrame sortByColumn(String column) {
        return sortByColumn(column, true);
    }

    // Return a new DataFrame sorted by `column`, access by index
    public DataFrame sortByColumnIndex(int columnIndex, boolean ascending) {

        // If columnIndex is out of range, throw an exception
        if (columnIndex < 0 || columnIndex >= getNumberOfColumns())
            throw new RuntimeException("[ERROR] Invalid index: cannot access column at index " + columnIndex + ".");

        var newData = new ArrayList<>(data);
        var newHeader = new ArrayList<>(header);

        // Sort the copied dataframe
        newData.sort(new Comparator<>() {

            // Compare the two data elements by numeric value or default string comparison
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {

                // Switch order for comparison if ascending = false
                String a = o1.get(columnIndex);
                String b = o2.get(columnIndex);

                // Convert strings to doubles and compare numerically
                if (isNumeric(a) && isNumeric(b)) {
                    Double aDouble = Double.parseDouble(a);
                    Double bDouble = Double.parseDouble(b);
                    return(aDouble.compareTo(bDouble));
                }

                // Compare alphabetically
                return a.compareToIgnoreCase(b);
            }

            // Check if string is numeric
            private boolean isNumeric(String string) {
                return Pattern.matches(NUMERIC_REGEX, string);
            }
        });

        if (!ascending)
            newData = new ArrayList<>(newData.reversed());

        return new DataFrame(newData, newHeader);
    }

    // Override: ascending = true by default
    public DataFrame sortByColumnIndex(int columnIndex) {
        return sortByColumnIndex(columnIndex, true);
    }
}
