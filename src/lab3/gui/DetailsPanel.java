
// Title: DetailsPanel.java
// Author: Kevin Nard
// Panel that shows details

package lab3.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DetailsPanel extends JPanel {

    private final GridPanel gridPanel = new GridPanel();

    public DetailsPanel(String title, Color color) {

        // Add padding around child components
        var border = new EmptyBorder(Theme.DEFAULT_INSETS);
        setBorder(border);

        // Set background to transparent
        setOpaque(false);

        // Set layout
        setLayout(new BorderLayout());

        // Add title and GridPanel
        add(new TitlePanel(title, color), BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
    }

    public void displayDetails(ArrayList<String> details) {

        // Remove all children from grid
        gridPanel.removeAll();

        // Add details as JLabels
        for (String detail : details) {
            int minWidth = 0;
            int minHeight = 0;
            int prefWidth = 100;
            int prefHeight = 15;
            JLabel detailLabel = new JLabel(detail);
            detailLabel.setMinimumSize(new Dimension(minWidth, minHeight));
            detailLabel.setPreferredSize(new Dimension(prefWidth, prefHeight));
            gridPanel.add(detailLabel);
        }

        revalidate();
        repaint();
    }

    private static class TitlePanel extends JPanel {
        TitlePanel(String text, Color color) {
            JLabel titleLabel = new JLabel(text);
            add(titleLabel);
            setBackground(color);
        }
    }

    private static class GridPanel extends JPanel {
        GridPanel() {
            int numRows = 10;
            int numCols = 1;
            setLayout(new GridLayout(numRows, numCols));
            setBorder(new EmptyBorder(Theme.DEFAULT_INSETS));
            setBackground(Theme.LIGHT_BACKGROUND_1);
        }
    }
}
