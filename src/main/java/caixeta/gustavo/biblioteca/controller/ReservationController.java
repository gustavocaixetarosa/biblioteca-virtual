package caixeta.gustavo.biblioteca.controller;

import caixeta.gustavo.biblioteca.model.Reservation;
import caixeta.gustavo.biblioteca.repository.BookRepository;
import caixeta.gustavo.biblioteca.repository.ReservationRepository;
import caixeta.gustavo.biblioteca.repository.UserRepository;
import caixeta.gustavo.biblioteca.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
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
    public ResponseEntity<Object> createReservation(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            Reservation reservation = reservationService.createReservation(userId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable Long id){
        List<Reservation> reservationList = reservationService.getReservationsByUser(id);

        return ResponseEntity.ok().body(reservationList);
    }

    @GetMapping("/user/open/{id}")
    public ResponseEntity<List<Reservation>> getNonReturnedReservationsByUser(@PathVariable Long id){
        Optional<List<Reservation>> nonReturned = reservationService.getNonReturned(id);
        if(nonReturned.isPresent())
            return ResponseEntity.ok(nonReturned.get());
        else
            throw new RuntimeException("All books was returned!!!");
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<Object> returnBook(@PathVariable Long id) {
        try {
            Reservation updatedReservation = reservationService.returnBook(id);
            return ResponseEntity.ok(updatedReservation);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> allReservations = reservationRepository.findAll();
        return ResponseEntity.ok(allReservations);
    }
}
