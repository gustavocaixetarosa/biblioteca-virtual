package caixeta.gustavo.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean disponibilide;

    @NotBlank(message = "The title must not be empty.")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "The author must not be empty.")
    @Size(max = 255)
    private String author;

    @NotBlank(message = "The isbn must not be empty.")
    private String isbn;

    private String publisher;

    private int publicationYear;

    private String genre;

    private int quantityAvailable;

    private int numberOfPages;

    private String language;

    private String resume;

    private String bookCover;

    private List<String> tags;
}
