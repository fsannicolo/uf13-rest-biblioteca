package it.marconi.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.marconi.biblioteca.domain.LibroDTO;
import it.marconi.biblioteca.services.LibroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/libri")
public class LibroController {
    
    @Autowired
    LibroService libroService;

    @GetMapping
    @Operation(summary = "Recupera la lista di tutti i libri")
    public List<LibroDTO> getAll() {

        return libroService.findAll();
    }

    @GetMapping("/{isbn}")
    @Operation(summary = "Cerca un libro dal sui ISBN")
    public ResponseEntity<LibroDTO> getLibroByIsbn(@PathVariable String isbn) {

        return libroService.getByIsbn(isbn)
            .map(libro -> ResponseEntity.ok(libro))     // versione lambda-function
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/libro")
    @Operation(summary = "Cerca un libro per titolo esatto")
    public ResponseEntity<LibroDTO> getLibroByTitolo(@RequestParam("titolo") String titolo) {

        return libroService.getByTitolo(titolo)
            .map(ResponseEntity::ok)                    // versione method reference
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    @Operation(summary = "Aggiunge un nuovo libro, dato l'autore")
    public ResponseEntity<LibroDTO> addLibro(@Valid @RequestBody LibroDTO libro) {
        
        return libroService.save(libro)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{isbn}")
    @Operation(summary = "Elimina un libro dato il suo ISBN")
    public ResponseEntity<String> deleteLibro(@PathVariable String isbn) {

        boolean deleted = libroService.deleteByIsbn(isbn);

        return deleted ? 
            ResponseEntity.ok("Libro eliminato correttamente!") : 
            ResponseEntity.notFound().build();
    }

}
