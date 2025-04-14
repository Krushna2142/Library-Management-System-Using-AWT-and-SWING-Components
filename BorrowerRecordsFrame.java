package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BorrowerRecordsFrame extends JFrame {

    private String borrowerUsername;

    public BorrowerRecordsFrame(String username) {
        this.borrowerUsername = username;

        setTitle("Borrower Records");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        displayBorrowRecords(textArea);
    }

    private void displayBorrowRecords(JTextArea textArea) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT b.title, br.borrow_date, br.due_date, br.return_date, br.fine_amount " +
                             "FROM BorrowRecords br " +
                             "JOIN Books b ON br.book_id = b.book_id " +
                             "JOIN Users u ON br.user_id = u.user_id " +
                             "WHERE u.username = ?")) {

            stmt.setString(1, borrowerUsername);

            ResultSet rs = stmt.executeQuery();
            StringBuilder records = new StringBuilder();

            while (rs.next()) {
                records.append("Title: ").append(rs.getString("title")).append("\n")
                        .append("Borrowed Date: ").append(rs.getDate("borrow_date")).append("\n")
                        .append("Due Date: ").append(rs.getDate("due_date")).append("\n")
                        .append("Return Date: ").append(rs.getDate("return_date") != null ? rs.getDate("return_date") : "Not Returned").append("\n")
                        .append("Fine Amount: ₹").append(rs.getDouble("fine_amount")).append("\n")
                        .append("---------------------------------------------------\n");
            }

            textArea.setText(records.length() > 0 ? records.toString() : "No borrow records found.");
        } catch (SQLException e) {
            e.printStackTrace();
            textArea.setText("Error fetching borrow records.");
        }
    }
}