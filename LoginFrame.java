package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginFrame() {
        setTitle("Library Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Librarian", "Borrower"});

        add(createFieldPanel("Username:", usernameField));
        add(createFieldPanel("Password:", passwordField));
        add(createFieldPanel("Role:", roleComboBox));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Create Account");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel);

        loginButton.addActionListener(new LoginAction());
        registerButton.addActionListener(e -> new RegisterFrame().setVisible(true));
    }

    private JPanel createFieldPanel(String label, JComponent field) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel(label, SwingConstants.RIGHT));
        panel.add(field);
        return panel;
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM Users WHERE username = ? AND password = ? AND role = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful!");
                    if (role.equals("Librarian")) {
                        new LibrarianDashboard().setVisible(true);
                    } else {
                        new BorrowerDashboard(username).setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid Credentials!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(LoginFrame.this, "Database Error!");
            }
        }
    }
}