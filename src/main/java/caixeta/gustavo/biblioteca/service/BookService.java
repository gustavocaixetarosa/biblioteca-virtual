package caixeta.gustavo.biblioteca.service;

import caixeta.gustavo.biblioteca.model.Book;
import caixeta.gustavo.biblioteca.model.dto.BookDTO;
import caixeta.gustavo.biblioteca.repository.BookRepository;
import caixeta.gustavo.biblioteca.validation.book.BookValidation;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private List<BookValidation> validations;

    public BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getQuantityAvailable());
    }

    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    public Book registerBook(Book book){
        validations.forEach(v -> v.validate(book));
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        if(!bookRepository.existsByIsbn(book.getIsbn()))
            throw new ValidationException("Book not registered!");

        book.setAvailable(book.getQuantityAvailable() > 0);
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