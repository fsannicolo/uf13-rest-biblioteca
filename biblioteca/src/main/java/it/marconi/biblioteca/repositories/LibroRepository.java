package it.marconi.biblioteca.repositories;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import it.marconi.biblioteca.domain.libro.Libro;

public interface LibroRepository extends JpaRepository<Libro, String> {
    
    List<Libro> findByAutoreId(int autoreId);

    @Transactional
    long deleteLibroByIsbn(String isbn);

}
