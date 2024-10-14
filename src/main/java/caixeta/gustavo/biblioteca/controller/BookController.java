package caixeta.gustavo.biblioteca.controller;

import caixeta.gustavo.biblioteca.model.Book;
import caixeta.gustavo.biblioteca.model.dto.BookDTO;
import caixeta.gustavo.biblioteca.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<Book> allBooks = bookService.listAll(); // Obter todos os livros como entidades
        List<BookDTO> bookDTOs = allBooks.stream() // Converter para DTOs
                .map(book -> bookService.convertToDTO(book)) // Usar um método para converter
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDTOs); // Retornar a lista de DTOs
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Book foundBook = bookService.searchById(id);
        return ResponseEntity.ok(foundBook);
    }


    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getBookByTitle(@PathVariable String title) {
        List<Book> livros = bookService.searchByTitle(title);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBookByAuthor(@PathVariable String author) {
        List<Book> livros = bookService.searchByAuthor(author);
        return ResponseEntity.ok(livros);
    }


    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book book){
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
        book.setId(id);
        Book updatedBook = bookService.saveBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
