package za.ac.tut.dao;

import za.ac.tut.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private static final String DB_URL = "jdbc:h2:mem:bookdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public BookDao() {
        try {
            Class.forName("org.h2.Driver"); // Load H2 driver
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "CREATE TABLE IF NOT EXISTS books (id INT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(255), author VARCHAR(255))";
                conn.createStatement().execute(sql);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize BookDao: " + e.getMessage(), e);
        }
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
        try {
            Class.forName("org.h2.Driver"); // Load H2 driver
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    book.setId(rs.getInt(1));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add book: " + e.getMessage(), e);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try {
            Class.forName("org.h2.Driver"); // Load H2 driver
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author")
                    );
                    books.add(book);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get books: " + e.getMessage(), e);
        }
        return books;
    }
}