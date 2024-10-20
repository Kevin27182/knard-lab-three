
// Title: ControlPanel.java
// Author: Kevin Nard
// Panel that shows controls

package lab3.gui;

import lab3.base.DataFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

        // Add change listeners for text fields
        left.addKeyListener(new TextFieldAction());
        right.addKeyListener(new TextFieldAction());

        // Drop down boxes for selecting comparison operators
        JComboBox<String> leftBox = new JComboBox<>();
        JComboBox<String> rightBox = new JComboBox<>();

        // Add operators to drop down boxes
        operators.forEach(leftBox::addItem);
        operators.forEach(rightBox::addItem);

        // Add action listeners for operator boxes
        leftBox.addActionListener(new OperatorAction(left));
        rightBox.addActionListener(new OperatorAction(right));

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
    private class OperatorAction implements ActionListener {

        private final JTextField field;

        // Set the field
        OperatorAction(JTextField field) {
            this.field = field;
        }

        // OperatorAction to perform on item selection
        @Override
        public void actionPerformed(ActionEvent e) {

            // Get source combo box and its selected item
            JComboBox<?> src = (JComboBox<?>) e.getSource();
            String item = (String) src.getSelectedItem();

            // Throw an error if item is null
            assert item != null;

            // Activate text field based on selected item
            activateTextField(field, !item.isBlank());
        }
    }

    // Reusable action listener for operator drop down boxes
    private static class TextFieldAction implements KeyListener {

        private static LocalDateTime Timeout;
        private static boolean updating = false;

        @Override
        public void keyTyped(KeyEvent e) {

            // Reset the timeout
            Timeout = LocalDateTime.now();

            // Do nothing if already updating
            if (updating) {
                return;
            }

            updating = true;

            // Defer timeout-based updating to new thread
            Thread thread = new Thread(this::deferUpdate);
            thread.start();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Do nothing
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Do nothing
        }

        // Wait for timeout to expire, then update
        private void deferUpdate() {

            int duration = 2;

            // Checks if timeout has expired once per second
            while (Timeout.plusSeconds(duration).isAfter(LocalDateTime.now())) {

                // Sleep for 1 second
                try { TimeUnit.SECONDS.sleep(1); }

                // If InterruptedException occurs, throw a RuntimeException (should never happen)
                catch (InterruptedException ex) { throw new RuntimeException(ex); }
            }

            updating = false;

            // Update logic
            System.out.println("Updating");
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
