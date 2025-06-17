package it.epicode.challengeu5w3d2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id ;
private String destinazione;
@Column(name = "data_del_viaggio")
private LocalDate dataViaggio;

    @Enumerated(EnumType.STRING)
private Stato statoViaggio;

    @JsonIgnore
    @OneToMany(mappedBy = "viaggio")
    private List<Prenotazione> prenotazioni;
}
