package it.marconi.biblioteca.controllers;

import it.marconi.biblioteca.services.LibroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class LibroControllerTest {

    @InjectMocks
    private LibroService libroService;

    @Test
    @DisplayName("Test getAll: Verifica che la lista dei libri venga restituita correttamente")
    void getLibroByIsbnCheckNotFound() {
        String isbn = "1234567890";
        ResponseEntity<?> response = libroService.getByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        assertEquals(404, response.getStatusCode(), "La risposta dovrebbe essere 404 Not Found quando il libro non esiste");
    }

    @Test
    void addLibro() {
    }
}