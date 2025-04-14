package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowBookFrame extends JFrame {

    private JTextField bookIdField;
    private String borrowerUsername;

    public BorrowBookFrame(String username) {
        this.borrowerUsername = username;

        setTitle("Borrow Book");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        JButton borrowButton = new JButton("Borrow Book");
        add(borrowButton);

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });
    }

    private void borrowBook() {
        int bookId = Integer.parseInt(bookIdField.getText());
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14); // 2 weeks borrowing duration

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO BorrowRecords (user_id, book_id, borrow_date, due_date) " +
                             "SELECT user_id, ?, ?, ? FROM Users WHERE username = ?")) {
            stmt.setInt(1, bookId);
            stmt.setDate(2, java.sql.Date.valueOf(borrowDate));
            stmt.setDate(3, java.sql.Date.valueOf(dueDate));
            stmt.setString(4, borrowerUsername);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error borrowing the book!");
        }
    }
}