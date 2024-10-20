
// Title: ControlPanel.java
// Author: Kevin Nard
// Panel that shows controls

package lab3.gui;

import lab3.base.DataFrame;
import lab3.base.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {

    private final JTextField left = new JTextField();
    private final JTextField right = new JTextField();
    private final JComboBox<String> leftBox = new JComboBox<>();
    private final JComboBox<String> rightBox = new JComboBox<>();
    private final JComboBox<String> fieldsBox = new JComboBox<>();
    private final DataFrame data;
    private Runnable updateUI = () -> {};
    private Consumer<DataFrame> updateDataDisplay;

    public ControlPanel(DataFrame data) {

        this.data = data;

        setBackground(Theme.LIGHT_BACKGROUND_1);

        // ArrayList of operators for filter
        ArrayList<String> operators = new ArrayList<>() {{
            add("");
            add("<");
            add("<=");
        }};

        // Deactivate both text fields
        activateTextField(left, false);
        activateTextField(right, false);

        // Set default size for text fields
        left.setPreferredSize(new Dimension(100,25));
        right.setPreferredSize(new Dimension(100,25));

        // Add change listeners for text fields
        left.addKeyListener(new TextFieldAction());
        right.addKeyListener(new TextFieldAction());

        // Add operators to drop down boxes
        operators.forEach(leftBox::addItem);
        operators.forEach(rightBox::addItem);

        // Add action listeners for operator boxes
        leftBox.addActionListener(new OperatorAction(left));
        rightBox.addActionListener(new OperatorAction(right));

        // Add fields from data
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
            update();
        }
    }

    // Reusable action listener for operator drop down boxes
    private class TextFieldAction implements KeyListener {

        // Filter the data after the text field is changed
        @Override
        public void keyTyped(KeyEvent e) {
            update();
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Do nothing
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Do nothing
        }
    }

    // Wrap `updateFilter` in new thread to prevent UI slowdown
    private void update() {
        Thread t = new Thread(this::updateFilter);
        t.start();
    }

    // Update the filter
    private void updateFilter() {

        // Do nothing if `updateDataDisplay` callback is not set
        if (updateDataDisplay == null)
            return;

        // Timeout necessary for thread to access updated information
        try { TimeUnit.MILLISECONDS.sleep(1); }
        catch (InterruptedException ex) {throw new RuntimeException(); }

        // Extract values from user input
        String leftValue = left.getText();
        String leftOperator = (String) leftBox.getSelectedItem();
        String field = (String) fieldsBox.getSelectedItem();
        String rightOperator = (String) rightBox.getSelectedItem();
        String rightValue = right.getText();

        // Left- and right-hand cast targets
        double l;
        double r;

        // Cast to double if numeric
        if (Validation.isNumeric(leftValue))
            l = Double.parseDouble(leftValue);

        // Reject filter if non-numeric
        else {
            l = 0;
            leftValue = "";
        }

        // Cast to double if numeric
        if (Validation.isNumeric(rightValue))
            r = Double.parseDouble(rightValue);

        // Reject filter if non-numeric
        else {
            r = 0;
            rightValue = "";
        }

        // Throw an error if any of the drop-down boxes contain a null value
        assert leftOperator != null;
        assert field != null;
        assert rightOperator != null;

        // Checks for emptiness
        boolean leftEmpty = leftValue.isBlank() || leftOperator.isBlank();
        boolean rightEmpty = rightValue.isBlank() || rightOperator.isBlank();

        // If you can think of a better way than using an if chain to implement this filter,
        // please describe it to me in detail in your review

        // No filter
        if (leftEmpty && rightEmpty) {
            updateDataDisplay.accept(data);
        }

        // left < field
        else if (!leftEmpty && rightEmpty && leftOperator.equals("<")) {
            updateDataDisplay.accept(data.filterNumeric(e -> l < e, field));
        }

        // left <= field
        else if (!leftEmpty && rightEmpty && leftOperator.equals("<=")) {
            updateDataDisplay.accept(data.filterNumeric(e -> l <= e, field));
        }

        // field < right
        else if (leftEmpty && rightOperator.equals("<")) {
            updateDataDisplay.accept(data.filterNumeric(e -> e < r, field));
        }

        // field <= right
        else if (leftEmpty && rightOperator.equals("<=")) {
            updateDataDisplay.accept(data.filterNumeric(e -> e <= r, field));
        }

        // left < field < right
        else if (leftOperator.equals("<") && rightOperator.equals("<")) {
            updateDataDisplay.accept(data.filterNumeric(e -> l < e && e < r, field));
        }

        // left <= field < right
        else if (leftOperator.equals("<=") && rightOperator.equals("<")) {
            updateDataDisplay.accept(data.filterNumeric(e -> l <= e && e < r, field));
        }

        // left < field <= right
        else if (leftOperator.equals("<") && rightOperator.equals("<=")) {
            updateDataDisplay.accept(data.filterNumeric(e -> l < e && e <= r, field));
        }

        // left <= field <= right
        else {
            updateDataDisplay.accept(data.filterNumeric(e -> l <= e && e <= r, field));
        }

        // Update the UI with new dataDisplay
        updateUI.run();
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

    // Set the UI update callback
    public void setUpdateUI(Runnable updateUI) {
        this.updateUI = updateUI;
    }

    // Set the display data
    public void setUpdateDataDisplay(Consumer<DataFrame> updateDataDisplay) {
        this.updateDataDisplay = updateDataDisplay;
    }
}
