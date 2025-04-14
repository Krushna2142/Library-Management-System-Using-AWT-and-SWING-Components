package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBooksFrame extends JFrame {

    public ViewBooksFrame() {
        setTitle("View Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Books")) {

            StringBuilder books = new StringBuilder();
            while (rs.next()) {
                books.append("ID: ").append(rs.getInt("book_id")).append(" | ")
                        .append("Title: ").append(rs.getString("title")).append(" | ")
                        .append("Author: ").append(rs.getString("author")).append(" | ")
                        .append("ISBN: ").append(rs.getString("isbn")).append(" | ")
                        .append("Quantity: ").append(rs.getInt("quantity")).append("\n");
            }
            textArea.setText(books.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            textArea.setText("Error fetching books from the database!");
        }
    }
}