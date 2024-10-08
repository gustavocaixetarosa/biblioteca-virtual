package caixeta.gustavo.biblioteca.service;

import caixeta.gustavo.biblioteca.model.Book;
import caixeta.gustavo.biblioteca.model.Reservation;
import caixeta.gustavo.biblioteca.model.User;
import caixeta.gustavo.biblioteca.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;


    @Transactional
    public Reservation createReservation(Long userId, Long bookId) {
        User user = userService.searchById(userId);
        Book book = bookService.searchById(bookId);

        if (book.getQuantityAvailable() <= 0) {
            throw new IllegalArgumentException("Book is not available.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setDueDate(LocalDate.now().plusWeeks(1));

        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        bookService.saveBook(book);

        return reservationRepository.save(reservation);
    }


    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }


    @Transactional
    public void returnBook(Long reservationId) {
        Reservation reservation = findById(reservationId);

        if (reservation.isReturned()) {
            throw new IllegalStateException("Book was returned.");
        }

        reservation.setReturned(true);

        Book book = reservation.getBook();
        book.setQuantityAvailable(book.getQuantityAvailable() + 1);
        bookService.saveBook(book);

        reservationRepository.save(reservation);
    }


    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));
    }


    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}