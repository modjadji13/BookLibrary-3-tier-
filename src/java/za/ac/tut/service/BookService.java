package za.ac.tut.service;

import za.ac.tut.dao.BookDao;
import za.ac.tut.model.Book;
import java.util.List;

public class BookService {
    private BookDao bookDao;

    public BookService() {
        this.bookDao = new BookDao();
    }

    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}