package caixeta.gustavo.biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;

    @BeforeEach
    public void setUp(){
        this.book = new Book();
        book.setTitle("Java: como programar");
        book.setAuthor("Paul Deitel");
        book.setQuantityAvailable(10);
        book.setAvailable(true);
    }

    @Test
    public void testBookCreation() {
        assertNotNull(book);
        assertEquals("Java: como programar", book.getTitle());
        assertEquals("Paul Deitel", book.getAuthor());
        assertEquals(10, book.getQuantityAvailable());
    }

    @Test
    public void testUpdateStock()   {
        // Adicionando ao estoque
        book.addStock(3);
        assertEquals(13, book.getQuantityAvailable());

        // Removendo do estoque
        book.reduceStock(2);
        assertEquals(11, book.getQuantityAvailable());
    }

    @Test
    public void testCheckAvailability() {
        assertTrue(book.isAvailable());

        // Reduzir o estoque a zero
        book.reduceStock(10);
        assertFalse(book.isAvailable());
    }
}