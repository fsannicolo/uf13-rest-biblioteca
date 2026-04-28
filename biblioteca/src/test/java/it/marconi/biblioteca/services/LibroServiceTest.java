package it.marconi.biblioteca.services;

import it.marconi.biblioteca.domain.libro.Libro;
import it.marconi.biblioteca.domain.libro.LibroDTO;
import it.marconi.biblioteca.repositories.AutoreRepository;
import it.marconi.biblioteca.repositories.LibroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepo;

    @Mock
    private AutoreRepository autoreRepo;

    @InjectMocks
    private LibroService libroSrv;

    @Test
    @DisplayName("Salvataggio libro fallito: Autore non trovato")
    void saveLibro_AutoreNonTrovato() {
        LibroDTO libroDTO = new LibroDTO(
                "1234567890",
                "Titolo di Test",
                "Fantasy",
                1,
                1
        );

        when(autoreRepo.findById(libroDTO.autore())).thenReturn(Optional.empty());

        Optional<LibroDTO> result = libroSrv.save(libroDTO);

        assertTrue(result.isEmpty(), "Il risultato dovrebbe essere vuoto quando l'autore non è trovato");

        verify(libroRepo, times(0)).save(any(Libro.class));
    }
}