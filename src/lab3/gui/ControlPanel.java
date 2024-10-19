
// Title: ControlPanel.java
// Author: Kevin Nard
// Panel that shows controls

package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControlPanel extends JPanel {

    public ControlPanel(DataFrame data, DataFrame dataDisplay) {

        setBackground(Theme.LIGHT_BACKGROUND_1);

        // ArrayList of operators for filter
        ArrayList<String> operators = new ArrayList<>() {{
            add("");
            add("<");
            add("<=");
        }};

        // Text fields for entering numeric filter
        JTextField left = new JTextField();
        JTextField right = new JTextField();

        // Deactivate both text fields
        activateTextField(left, false);
        activateTextField(right, false);

        // Set default size for text fields
        left.setPreferredSize(new Dimension(100,25));
        right.setPreferredSize(new Dimension(100,25));

        // Drop down boxes for selecting comparison operators
        JComboBox<String> leftBox = new JComboBox<>();
        JComboBox<String> rightBox = new JComboBox<>();

        // Add operators to drop down boxes
        operators.forEach(leftBox::addItem);
        operators.forEach(rightBox::addItem);

        // Add action listeners for operator boxes
        leftBox.addActionListener(new Action(left));
        rightBox.addActionListener(new Action(right));

        // Add fields from data
        JComboBox<String> fieldsBox = new JComboBox<>();
        data.getHeader().forEach(fieldsBox::addItem);

        // Add components
        add(left);
        add(leftBox);
        add(fieldsBox);
        add(rightBox);
        add(right);
    }

    // Reusable action listener for operator drop down boxes
    private class Action implements ActionListener {

        private final JTextField field;

        // Set the field
        Action(JTextField field) {
            this.field = field;
        }

        // Action to perform on item selection
        @Override
        public void actionPerformed(ActionEvent e) {

            // Get combo box source and selected item
            JComboBox<String> src = (JComboBox<String>) e.getSource();
            String item = (String) src.getSelectedItem();

            // Activate text field based on selected item
            if (item != null)
                activateTextField(field, !item.isBlank());
        }
    }

    // Activate or deactivate the given text field
    private void activateTextField(JTextField field, boolean activated) {

        // Activate
        if (activated) {
            field.setBackground(Color.WHITE);
            field.setFocusable(true);
            field.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        }

        // Deactivate
        else {
            field.setBackground(Theme.LIGHT_BACKGROUND_1);
            field.setFocusable(false);
            field.setCursor(Cursor.getDefaultCursor());
        }
    }
}
