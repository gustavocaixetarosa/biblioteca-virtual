package caixeta.gustavo.biblioteca.validation.book;

import caixeta.gustavo.biblioteca.model.Book;
import org.springframework.stereotype.Component;

@Component
public class IsBookAvailableValidation implements BookValidation{

    @Override
    public void validate(Book book) {
        book.setAvailable(book.getQuantityAvailable() > 0);
    }
}
