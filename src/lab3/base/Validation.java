
// Title: Validation.java
// Author: Kevin Nard
// Contains static methods for performing data validation

package lab3.base;

import java.util.regex.Pattern;

public class Validation {

    private static final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";

    // Check if string is numeric
    public static boolean isNumeric(String string) {
        return Pattern.matches(NUMERIC_REGEX, string);
    }
}
