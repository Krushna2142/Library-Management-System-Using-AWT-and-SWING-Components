package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public RegisterFrame() {
        setTitle("Create Account");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Librarian", "Borrower"});

        add(createFieldPanel("Username:", usernameField));
        add(createFieldPanel("Password:", passwordField));
        add(createFieldPanel("Role:", roleComboBox));

        JButton registerButton = new JButton("Register");
        add(registerButton);

        registerButton.addActionListener(new RegisterAction());
    }

    private JPanel createFieldPanel(String label, JComponent field) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel(label, SwingConstants.RIGHT));
        panel.add(field);
        return panel;
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)")) {
                stmt.setString(1, username);
                stmt.setString(2, password); // Add encryption in production
                stmt.setString(3, role);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(RegisterFrame.this, "Account Created Successfully!");
                dispose();
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062) { // MySQL error code for duplicate entry
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Username already exists! Please choose a different username.");
                } else {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Database Error! Could not create account.");
                }
            }
        }
    }
}