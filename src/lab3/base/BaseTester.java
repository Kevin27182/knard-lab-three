package lab3.base;

import java.io.IOException;

public class BaseTester {

    public static void main(String[] args) throws IOException {

        DataFrame myFrame;
        try {
            myFrame = new DataFrame("data/wine.csv", true);
            String dataView = myFrame.glimpse();
            System.out.println(dataView);
        }
        catch (IOException e) {
            System.out.println("[ERROR] File not found: " + e.getMessage());
        }

    }
}
