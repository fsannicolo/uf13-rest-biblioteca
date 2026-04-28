package it.marconi.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import it.marconi.biblioteca.domain.generics.APIResponse;
import it.marconi.biblioteca.domain.libro.Libro;
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
import it.marconi.biblioteca.domain.libro.LibroDTO;
import it.marconi.biblioteca.services.LibroService;
import jakarta.validation.Valid;

import javax.swing.text.html.Option;

@RestController
@RequestMapping("/libri")
public class LibroController {
    
    @Autowired
    LibroService libroService;

    @GetMapping
    @Operation(summary = "Recupera la lista di tutti i libri")
    public APIResponse<List<LibroDTO>> getAll() {
        return APIResponse.success(libroService.findAll());
    }

    @GetMapping("/{isbn}")
    @Operation(summary = "Cerca un libro dal sui ISBN")
    public APIResponse<Optional<LibroDTO>> getLibroByIsbn(@PathVariable String isbn) {
        return APIResponse.success(libroService.getByIsbn(isbn));
    }

    @GetMapping("/libro")
    @Operation(summary = "Cerca un libro per titolo esatto")
    public APIResponse<Optional<LibroDTO>> getLibroByTitolo(@RequestParam("titolo") String titolo) {
        return APIResponse.success(libroService.getByTitolo(titolo));
    }

    @PostMapping("/add")
    @Operation(summary = "Aggiunge un nuovo libro, dato l'autore")
    public APIResponse<Optional<LibroDTO>> addLibro(@Valid @RequestBody LibroDTO libro) {
        return APIResponse.success(libroService.save(libro));
    }

    @DeleteMapping("/{isbn}")
    @Operation(summary = "Elimina un libro dato il suo ISBN")
    public ResponseEntity<Void> deleteLibro(@PathVariable String isbn) {
        libroService.deleteByIsbn(isbn);

        /*
            Nel caso della DELETE possiamo rispondere in tre modi:
                - 204 No Content - Non inviamo nulla al richiedente, comunichiamo che tutto è andato bene
                - 200 OK - Qui dobbiamo restituire qualcosa nel body, come un messaggio o l'id del libro eliminato
                - 202 Accepted - La richiesta è stata presa in carico dal server ma non ancora processata

            -> Nel codice restituiamo 204 No Content

            REF: https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Methods/DELETE
         */
        return ResponseEntity.noContent().build();
    }

}
