package za.ac.tut.web;

import za.ac.tut.model.Book;
import za.ac.tut.service.BookService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() {
        try {
            bookService = new BookService();
        } catch (Exception e) {
            System.err.println("Failed to initialize BookServlet: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Servlet initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);
            String jspPath = "/books.jsp"; // Updated to root JSP
            if (getServletContext().getResource(jspPath) == null) {
                System.err.println("JSP file not found at: " + jspPath);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "JSP file not found: " + jspPath);
                return;
            }
            request.getRequestDispatcher(jspPath).forward(request, response);
        } catch (Exception e) {
            System.err.println("Error in doGet: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading books: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            Book book = new Book(0, title, author);
            bookService.addBook(book);
            response.sendRedirect("books");
        } catch (Exception e) {
            System.err.println("Error in doPost: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding book: " + e.getMessage());
        }
    }
}