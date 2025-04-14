CREATE DATABASE IF NOT EXISTS LibraryManagementSystem;

USE LibraryManagementSystem;

CREATE TABLE IF NOT EXISTS Users (
                                     user_id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     role ENUM('Librarian', 'Borrower') NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE BorrowRecords

    ADD COLUMN due_date DATE NOT NULL,
    ADD COLUMN return_date DATE NULL,
    ADD COLUMN fine_amount DOUBLE DEFAULT 0;