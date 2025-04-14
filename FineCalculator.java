package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {
    private static final double FINE_PER_DAY = 10.0;

    public static void calculateFines() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             PreparedStatement updateStmt = conn.prepareStatement(
                     "UPDATE BorrowRecords SET fine_amount = ? WHERE borrow_id = ?")) {

            // Fetch overdue borrow records
            ResultSet rs = stmt.executeQuery(
                    "SELECT borrow_id, due_date, return_date FROM BorrowRecords WHERE return_date IS NULL");
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                LocalDate today = LocalDate.now();

                if (today.isAfter(dueDate)) {
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
                    double fine = daysOverdue * FINE_PER_DAY;

                    // Update fine in the database
                    updateStmt.setDouble(1, fine);
                    updateStmt.setInt(2, borrowId);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}