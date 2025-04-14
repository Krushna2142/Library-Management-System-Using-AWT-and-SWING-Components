package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BorrowerDashboard extends JFrame {
    private String borrowerUsername;

    public BorrowerDashboard(String username) {
        this.borrowerUsername = username;

        setTitle("Borrower Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        JButton viewBooksButton = new JButton("View Books");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton checkNotificationsButton = new JButton("Check Notifications");
        JButton viewBorrowRecordsButton = new JButton("View Borrow Records");
        JButton backButton = new JButton("Back to Login");

        add(viewBooksButton);
        add(borrowBookButton);
        add(checkNotificationsButton);
        add(viewBorrowRecordsButton);
        add(backButton);

        viewBooksButton.addActionListener(e -> new ViewBooksFrame().setVisible(true));
        borrowBookButton.addActionListener(e -> new BorrowBookFrame(borrowerUsername).setVisible(true));
        checkNotificationsButton.addActionListener(e -> new NotificationsFrame(borrowerUsername).setVisible(true));
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();

        });

        viewBorrowRecordsButton.addActionListener(e -> new BorrowerRecordsFrame(borrowerUsername).setVisible(true));
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
            });
    }
}

// ViewBooksFrame, BorrowBookFrame, and NotificationsFrame need to be implemented separately.