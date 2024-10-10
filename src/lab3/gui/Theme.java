
// Title: Theme.java
// Author: Kevin Nard
// Controller for UI theme

package lab3.gui;

import java.awt.*;

class Theme {

    // Theme private constants for switching only
    private static final Color LIGHT_BACKGROUND_1 = new Color(0xced6e0);
    private static final Color LIGHT_BACKGROUND_2 = new Color(0xdfe4ea);
    private static final Color LIGHT_BACKGROUND_3 = new Color(0xf1f2f6);
    private static final Color LIGHT_BACKGROUND_4 = new Color(0xffffff);
    private static final Color LIGHT_TEXT = new Color(0x191D23);
    private static final Color LIGHT_SUBTEXT = new Color(0x2F3039);
    private static final Color DARK_BACKGROUND_1 = new Color(0x2f3542);
    private static final Color DARK_BACKGROUND_2 = new Color(0x57606f);
    private static final Color DARK_BACKGROUND_3 = new Color(0x747d8c);
    private static final Color DARK_BACKGROUND_4 = new Color(0x747d8c);
    private static final Color DARK_TEXT = new Color(0xffffff);
    private static final Color DARK_SUBTEXT = new Color(0xced6e0);

    // Theme constants for general use
    public static final String WINDOW_TITLE = "Wine Data Analysis";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final Color PINK = new Color(0xFF7F92);
    public static final Color RED = new Color(0xff4757);
    public static final Color TOMATO = new Color(0Xff6348);
    public static final Color CORAL = new Color(0Xff7f50);
    public static final Color ORANGE = new Color(0xffa502);
    public static final Color YELLOW = new Color(0xeccc68);
    public static final Color LIME = new Color(0x7FED7B);
    public static final Color GREEN = new Color(0x2ed573);
    public static final Color BLUE_1 = new Color(0x3742fa);
    public static final Color BLUE_2 = new Color(0x1e90ff);
    public static final Color BLUE_3 = new Color(0x70a1ff);
    public static final Color MAUVE = new Color(0x8684D5);
    public static final Color PURPLE = new Color(0x7B2FE8);
    public static final Color MAGENTA = new Color(0x9D5BB6);

    // Theme dynamic variables
    public static Color BACKGROUND_1 = LIGHT_BACKGROUND_1;
    public static Color BACKGROUND_2 = LIGHT_BACKGROUND_2;
    public static Color BACKGROUND_3 = LIGHT_BACKGROUND_3;
    public static Color BACKGROUND_4 = LIGHT_BACKGROUND_4;
    public static Color TEXT = LIGHT_TEXT;
    public static Color SUBTEXT = LIGHT_SUBTEXT;

    // Enum for theme switching
    public enum Type {LIGHT, DARK}

    // Set the theme type
    public static void setTheme(Type type, Runnable update) {

        switch (type) {

            // Switch to light theme
            case LIGHT:
                BACKGROUND_1 = LIGHT_BACKGROUND_1;
                BACKGROUND_2 = LIGHT_BACKGROUND_2;
                BACKGROUND_3 = LIGHT_BACKGROUND_3;
                BACKGROUND_4 = LIGHT_BACKGROUND_4;
                TEXT = LIGHT_TEXT;
                SUBTEXT = LIGHT_SUBTEXT;
                break;

            // Switch to dark theme
            case DARK:
                BACKGROUND_1 = DARK_BACKGROUND_1;
                BACKGROUND_2 = DARK_BACKGROUND_2;
                BACKGROUND_3 = DARK_BACKGROUND_3;
                BACKGROUND_4 = DARK_BACKGROUND_4;
                TEXT = DARK_TEXT;
                SUBTEXT = DARK_SUBTEXT;
                break;
        }

        update.run();
    }

    // Set the theme type without updating UI
    public static void setTheme(Type type) {
        setTheme(type, () -> {});
    }
}
