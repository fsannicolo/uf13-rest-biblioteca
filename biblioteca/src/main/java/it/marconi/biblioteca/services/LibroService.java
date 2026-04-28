package it.marconi.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.marconi.biblioteca.domain.autore.Autore;
import it.marconi.biblioteca.domain.libro.Libro;
import it.marconi.biblioteca.domain.libro.LibroDTO;
import it.marconi.biblioteca.domain.libro.LibroMapper;
import it.marconi.biblioteca.repositories.AutoreRepository;
import it.marconi.biblioteca.repositories.LibroRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepo;

    @Autowired
    private AutoreRepository autoreRepo;

    @Autowired
    private LibroMapper mapper;

    public List<LibroDTO> findAll() {
        
        // converto la lista di libri in una lista di DTO
        return libroRepo.findAll().stream().map(mapper::toDto).toList();
    }

    public Optional<LibroDTO> getByIsbn(String isbn) {

        return libroRepo.findById(isbn).map(mapper::toDto);
    }

    public Optional<LibroDTO> getByTitolo(String titolo) {

        return libroRepo.findAll().stream()
            .filter(libro -> libro.getTitolo().equals(titolo)).findFirst()
            .map(mapper::toDto);
    }

    // ricerca dei libro per autore_id (query custom)
    public List<LibroDTO> getByAutoreId(int autoreId) {

        return libroRepo.findByAutoreId(autoreId).stream().map(mapper::toDto).toList();
    }

    public Optional<LibroDTO> save(LibroDTO libro) {

        Optional<Autore> autore = autoreRepo.findById(libro.autore());
        
        if (autore.isPresent()) {
            Autore a = autore.get();
            Libro entity = mapper.toEntity(libro, a);
            entity = libroRepo.save(entity);
            return Optional.of(mapper.toDto(entity));
        }
        else
            return Optional.empty();

        // cerco l'autore nel db da associare al libro
        // Autore autore = autoreRepo.findById(libro.autore()).
        //     orElseThrow(() -> new RuntimeException("Autore non trovato"));

        // Libro entity = mapper.toEntity(libro, autore);
        // entity = libroRepo.save(entity);
        // return mapper.toDto(entity);
    }

    public void deleteByIsbn(String isbn) {
        long deleteCount = libroRepo.deleteLibroByIsbn(isbn);

        if (deleteCount == 0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Impossibile eliminare: Libro con ISBN " + isbn + " non trovato"
            );
        }
    }

}
