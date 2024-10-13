
// Title: DataFrame.java
// Author: Kevin Nard
// Data structure that holds tabular data

package lab3.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DataFrame {

    private ArrayList<String> header;
    private ArrayList<ArrayList<String>> data = new ArrayList<>();
    private final String CSV_REGEX = "[,\n]";

    // Construct a new data frame
    public DataFrame(String path) throws IOException {
        this(path, true);
    }

    // Construct a new data frame, header optional
    public DataFrame(String path, boolean header) throws IOException {
        readCSV(path, header);
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

        // Add the header to `contents` if it exists and reformat startDataStream
        if (header != null) {
            contents += header + "\n";
            startDataStream = data.stream().skip(1).limit(3);
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

        return contents;
    }

    // Return an ArrayList representation of a column, access by name
    public ArrayList<String> getColumn(String column) {

        int columnIndex = header.indexOf(column);
        return getColumnAtIndex(columnIndex);
    }

    // Return an ArrayList representation of a column, access by index
    public ArrayList<String> getColumnAtIndex(int columnIndex) {

        // If the column index is out of range, return an empty ArrayList
        if (columnIndex < 0 || columnIndex >= data.getFirst().size())
            return new ArrayList<>();

        // Extract a List of the elements corresponding to `column`
        var columnList = data.stream().map(e -> e.get(columnIndex)).toList();

        // Cast to ArrayList<> and return
        return new ArrayList<>(columnList);
    }
}
