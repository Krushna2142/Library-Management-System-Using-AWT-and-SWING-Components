package org.example;

import javax.swing.*;
import java.awt.*;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        // Set a modern look and feel for the application
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to set look and feel. Default will be used.");
        }

        // Show a splash screen before launching the login frame
        showSplashScreen();

        // Launch the Login Frame
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        JPanel content = (JPanel) splash.getContentPane();

        // Splash screen design
        content.setBackground(new Color(60, 63, 65));
        content.setLayout(new BorderLayout());
        JLabel label = new JLabel("Library Management System", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);

        JLabel subLabel = new JLabel("Managing books made easy!", SwingConstants.CENTER);
        subLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subLabel.setForeground(Color.LIGHT_GRAY);

        content.add(label, BorderLayout.CENTER);
        content.add(subLabel, BorderLayout.SOUTH);

        // Display the splash screen for 2 seconds
        splash.setSize(400, 200);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            splash.dispose();
        }
    }
}