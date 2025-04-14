package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class NotificationsFrame extends JFrame {

    private String borrowerUsername;

    public NotificationsFrame(String username) {
        this.borrowerUsername = username;

        setTitle("Notifications");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT n.message FROM Notifications n " +
                             "JOIN Users u ON n.user_id = u.user_id " +
                             "WHERE u.username = '" + borrowerUsername + "'")) {

            StringBuilder notifications = new StringBuilder();
            while (rs.next()) {
                notifications.append(rs.getString("message")).append("\n");
            }
            textArea.setText(notifications.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            textArea.setText("Error fetching notifications from the database!");
        }
    }
}