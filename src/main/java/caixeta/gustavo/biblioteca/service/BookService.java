package caixeta.gustavo.biblioteca.service;

import caixeta.gustavo.biblioteca.model.Book;
import caixeta.gustavo.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        book.setDisponibilide(book.getQuantityAvailable() > 0);
        return bookRepository.save(book);
    }

    public Book searchById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found!"));
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}