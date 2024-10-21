package caixeta.gustavo.biblioteca.service;

import caixeta.gustavo.biblioteca.model.Book;
import caixeta.gustavo.biblioteca.model.Reservation;
import caixeta.gustavo.biblioteca.model.User;
import caixeta.gustavo.biblioteca.repository.ReservationRepository;
import caixeta.gustavo.biblioteca.repository.UserRepository;
import caixeta.gustavo.biblioteca.validation.book.BookValidation;
import caixeta.gustavo.biblioteca.validation.book.IsBookAvailableValidation;
import caixeta.gustavo.biblioteca.validation.user.UserValidation;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private List<UserValidation> userValidations;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IsBookAvailableValidation bookAvailableValidation;

    @Transactional
    public Reservation createReservation(Long userId, Long bookId) {
        User user = userService.searchById(userId);
        Book book = bookService.searchById(bookId);

        userValidations.forEach(uv -> uv.validate(user));

        bookAvailableValidation.validate(book);

        if(book.getQuantityAvailable() <= 0)
            throw new RuntimeException("Book not available");

//        if(userRepository.countReservationsByUser_Id(user.getId()) > 2)
//            throw new ValidationException(String.format("User with ID %s has reached the maximum limit of reservations: %d.", user.getId(), user.getMaxReservation()));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setDueDate(LocalDate.now().plusWeeks(1));

        book.setQuantityAvailable(book.getQuantityAvailable() - 1);

        bookService.updateBook(book);

        return reservationRepository.save(reservation);
    }


    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }


    @Transactional
    public Reservation returnBook(Long reservationId) {
        Reservation reservation = findById(reservationId);

        if (reservation.isReturned()) {
            throw new IllegalStateException("Book was already returned.");
        }

        reservation.setReturned(true);

        Book book = reservation.getBook();
        book.setQuantityAvailable(book.getQuantityAvailable() + 1);


        bookService.updateBook(book);

        return reservationRepository.save(reservation);
    }


    public Reservation findById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));
    }


    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.getReferenceById(reservationId);
        if (reservation.isReturned())
            reservationRepository.deleteById(reservationId);
        else
            throw new RuntimeException("Only reservations with book returned can be deleted!");
    }

    public Optional<List<Reservation>> getNonReturned(Long id) {

        return Optional.ofNullable(reservationRepository.findByUserIdAndIsReturnedFalse(id));
    }
}