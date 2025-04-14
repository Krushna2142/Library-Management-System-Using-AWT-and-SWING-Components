# Library Management System

A Java-based **Library Management System** that enables librarians and borrowers to manage library resources efficiently. The system includes features for adding books, managing borrowers, viewing borrow records, calculating overdue fines, and more. It uses **MySQL** as the backend database and **Swing** for the graphical user interface (GUI).

## Features

### For Librarians:
- Add new books to the library.
- Add new borrowers to the system.
- View all books in the library.
- Manage borrow records.

### For Borrowers:
- Borrow books from the library.
- View borrowed books along with:
  - Borrow date.
  - Due date.
  - Return date (if applicable).
  - Fine for overdue books (₹10/day).
- Notifications for overdue books.

### Fine Calculation:
- Automatically calculates fines for overdue books at ₹10 per day if books are not returned before the due date.

## Database Schema

### Tables:
1. **Books**:
    - `book_id` (INT, Primary Key)
    - `title` (VARCHAR)
    - `author` (VARCHAR)
    - `isbn` (VARCHAR)
    - `quantity` (INT)

2. **Users**:
    - `user_id` (INT, Primary Key)
    - `username` (VARCHAR, Unique)
    - `password` (VARCHAR)
    - `role` (ENUM - 'Librarian', 'Borrower')
    - `created_at` (TIMESTAMP)

3. **BorrowRecords**:
    - `borrow_id` (INT, Primary Key)
    - `user_id` (INT, Foreign Key)
    - `book_id` (INT, Foreign Key)
    - `borrow_date` (DATE)
    - `due_date` (DATE)
    - `return_date` (DATE, Nullable)
    - `fine_amount` (DOUBLE)

4. **Notifications**:
    - `notification_id` (INT, Primary Key)
    - `user_id` (INT, Foreign Key)
    - `message` (TEXT)

## Getting Started

### Prerequisites
- **Java Development Kit (JDK)** (Version 8 or above)
- **MySQL Database**
- **IDE** (e.g., IntelliJ IDEA, Eclipse, or NetBeans)

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/<your-username>/LibraryManagementSystem.git
    cd LibraryManagementSystem
    ```

2. Set up the database:
    - Import the provided `schema.sql` file into your MySQL database:
      ```bash
      mysql -u root -p < schema.sql
      ```

3. Add the MySQL Connector:
    - If using Maven, add this dependency to your `pom.xml`:
      ```xml
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>8.0.32</version>
      </dependency>
      ```
    - If not using Maven, download the [MySQL Connector JAR](https://dev.mysql.com/downloads/connector/j/) and add it to your classpath.

4. Configure the database connection in `DBConnection.java`:
    ```java
    private static final String URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password_here";
    ```

### Running the Application
1. Compile the project:
    ```bash
    javac -d bin src/**/*.java
    ```

2. Run the main class:
    ```bash
    java -cp bin org.example.LibraryManagementSystem
    ```

## Screenshots

### Librarian Dashboard
![Librarian Dashboard](screenshots/librarian_dashboard.png)

### Borrower Dashboard
![Borrower Dashboard](screenshots/borrower_dashboard.png)

### Borrow Records with Overdue Fines
![Borrow Records](screenshots/borrow_records.png)

## Fine Calculation Logic
- **Fine Rate**: ₹10 per day.
- Fines are calculated dynamically based on the `due_date` and the current date.
- Fines are updated in the `fine_amount` column of the `BorrowRecords` table.

## Contribution
We welcome contributions to improve the system. To contribute:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature"
   ```
4. Push the branch:
   ```bash
   git push origin feature/your-feature
   ```
5. Open a Pull Request.

## License
This project is licensed under the Jetbrains License. See the `LICENSE` file for details.

## Authors
- (<Krushna2142>)

## Acknowledgments
- Inspired by the needs of libraries to manage resources efficiently.
- Thanks to [MySQL](https://www.mysql.com/) and [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/) for making this project possible.
