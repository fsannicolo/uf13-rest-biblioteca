package it.marconi.biblioteca.domain;

import jakarta.validation.constraints.NotBlank;

public record AutoreDTO(

    //@NotBlank(message = "ID non può essere nullo")
    Integer id,

    @NotBlank(message = "Il nome è obbligatorio")
    String nome,

    @NotBlank(message = "Il cognome è obbligatorio")
    String cognome
) {}
