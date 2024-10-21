package caixeta.gustavo.biblioteca.validation.user;

import caixeta.gustavo.biblioteca.model.User;
import caixeta.gustavo.biblioteca.repository.ReservationRepository;
import caixeta.gustavo.biblioteca.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHasMaxReservations implements UserValidation{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public void validate(User user){
        if(reservationRepository.countByUser_IdAndIsReturnedFalse(user.getId()) >= 2)
            throw new ValidationException(String.format("User with ID %s has reached the maximum limit of reservations: %d.", user.getId(), user.getMaxReservation()));
    }
}
