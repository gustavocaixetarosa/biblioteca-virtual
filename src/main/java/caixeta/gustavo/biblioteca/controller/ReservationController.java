package caixeta.gustavo.biblioteca.controller;

import caixeta.gustavo.biblioteca.model.Book;
import caixeta.gustavo.biblioteca.model.Reservation;
import caixeta.gustavo.biblioteca.model.User;
import caixeta.gustavo.biblioteca.repository.BookRepository;
import caixeta.gustavo.biblioteca.repository.ReservationRepository;
import caixeta.gustavo.biblioteca.repository.UserRepository;
import caixeta.gustavo.biblioteca.service.BookService;
import caixeta.gustavo.biblioteca.service.ReservationService;
import caixeta.gustavo.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestParam Long userId, @RequestParam Long bookId) {
        // Busca o usuário e o livro pelos IDs fornecidos
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (!bookOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Book book = bookOptional.get();

        if (book.getQuantityAvailable() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Reservation reservation = new Reservation();
        reservation.setUser(userOptional.get());
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());

        reservation.setDueDate(LocalDate.now().plusDays(7));
        reservation.setReturned(false);

        Reservation savedReservation = reservationRepository.save(reservation);

        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

}
