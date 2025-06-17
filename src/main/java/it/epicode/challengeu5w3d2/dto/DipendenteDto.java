package it.epicode.challengeu5w3d2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DipendenteDto {
    @NotEmpty(message = "l'username non può essere nullo o vuoto")
    private String userName;
    @NotEmpty(message = "il nome non può essere nullo o vuoto")
    private String nome;
    @NotEmpty(message = "il cognome non può essere nullo o vuoto")
    private String cognome;
    @Email(message = "deve essere un indirizzo email valido!")
    private String email;
}
