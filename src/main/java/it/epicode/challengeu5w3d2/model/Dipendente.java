package it.epicode.challengeu5w3d2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

    @Column(name = "user_name")
private String userName;
private String nome;
private String cognome;
private String email;
@Column(name = "immagine_del_profilo")
private String immagineProfilo;

@JsonIgnore
@OneToMany(mappedBy = "dipendente")
    private List<Prenotazione>prenotazioni;
}
