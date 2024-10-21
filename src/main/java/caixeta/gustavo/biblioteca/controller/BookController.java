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
        List<Book> allBooks = bookService.listAll();
        List<BookDTO> allBooksDTO = allBooks.stream()
                .map(book -> bookService.convertToDTO(book))
                .collect(Collectors.toList());
        return ResponseEntity.ok(allBooksDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        BookDTO foundBookDTO = this.bookService.convertToDTO(bookService.searchById(id));
        return ResponseEntity.ok(foundBookDTO);
    }


    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDTO>> getBookByTitle(@PathVariable String title) {
        List<Book> livros = bookService.searchByTitle(title);
        List<BookDTO> listDTO = livros.stream().map(bookService::convertToDTO).toList();
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO>> getBookByAuthor(@PathVariable String author) {
        List<Book> books = bookService.searchByAuthor(author);
        List<BookDTO> bookdsDTO = books.stream().map(bookService::convertToDTO).toList();
        return ResponseEntity.ok(bookdsDTO);
    }


    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book book){
        Book savedBook = bookService.registerBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody @Valid Book book) {
        book.setId(id);
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
