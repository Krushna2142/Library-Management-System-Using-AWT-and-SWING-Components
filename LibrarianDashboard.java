package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LibrarianDashboard extends JFrame {
    public LibrarianDashboard() {
        setTitle("Librarian Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        JButton addBookButton = new JButton("Add Book");
        JButton addBorrowerButton = new JButton("Add Borrower");
        JButton viewBooksButton = new JButton("View Books");
        JButton checkAvailabilityButton = new JButton("Check Book Availability");
        JButton backButton = new JButton("Back to Login");

        add(addBookButton);
        add(addBorrowerButton);
        add(viewBooksButton);
        add(checkAvailabilityButton);
        add(backButton);

        addBookButton.addActionListener(e -> new AddBookFrame().setVisible(true));
        addBorrowerButton.addActionListener(e -> new AddBorrowerFrame().setVisible(true));
        viewBooksButton.addActionListener(e -> new ViewBooksFrame().setVisible(true));
        checkAvailabilityButton.addActionListener(e -> new CheckBookAvailabilityFrame().setVisible(true));
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}

// AddBookFrame, AddBorrowerFrame, ViewBooksFrame, and CheckBookAvailabilityFrame need to be implemented separately.