package it.marconi.biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.marconi.biblioteca.domain.Libro;

public interface LibroRepository extends JpaRepository<Libro, String> {
    
    List<Libro> findByAutoreId(int autoreId);
}
