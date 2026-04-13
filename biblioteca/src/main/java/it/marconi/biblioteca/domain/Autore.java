package it.marconi.biblioteca.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors") 
public class Autore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Integer id;

    @NotNull    // a livello API
    @Size(max = 30)
    @Column(name = "name", nullable = false)    // a livello DB
    private String nome;

    @NotNull
    @Size(max = 30)
    @Column(name = "surname", nullable = false)
    private String cognome;

    @OneToMany(mappedBy = "autore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libri = new ArrayList<>();

    // metodo handler
    public void addLibro(Libro libro) {
        libri.add(libro);
        libro.setAutore(this); 
    }
}
