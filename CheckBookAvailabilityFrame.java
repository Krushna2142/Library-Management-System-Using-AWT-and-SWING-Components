package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckBookAvailabilityFrame extends JFrame {

    private JTextField bookIdOrTitleField;
    private JTextArea resultArea;

    public CheckBookAvailabilityFrame() {
        setTitle("Check Book Availability");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Enter Book ID or Title:"));
        bookIdOrTitleField = new JTextField();
        inputPanel.add(bookIdOrTitleField);

        JButton checkButton = new JButton("Check Availability");
        inputPanel.add(checkButton);
        add(inputPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listener for Check Button
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBookAvailability();
            }
        });
    }

    private void checkBookAvailability() {
        String input = bookIdOrTitleField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Book ID or Title!");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM Books WHERE book_id = ? OR title LIKE ?")) {
            // Check by ID or Title
            stmt.setString(1, input); // Check by ID
            stmt.setString(2, "%" + input + "%"); // Check by Title

            ResultSet rs = stmt.executeQuery();
            StringBuilder result = new StringBuilder();

            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("book_id")).append("\n")
                        .append("Title: ").append(rs.getString("title")).append("\n")
                        .append("Author: ").append(rs.getString("author")).append("\n")
                        .append("ISBN: ").append(rs.getString("isbn")).append("\n")
                        .append("Available Quantity: ").append(rs.getInt("quantity")).append("\n")
                        .append("---------------------------------------------------\n");
            }

            if (result.length() == 0) {
                resultArea.setText("No books found matching the given ID or Title.");
            } else {
                resultArea.setText(result.toString());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking book availability!");
        }
    }
}