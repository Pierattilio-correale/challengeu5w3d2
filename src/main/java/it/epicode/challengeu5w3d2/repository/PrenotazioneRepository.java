package it.epicode.challengeu5w3d2.repository;

import it.epicode.challengeu5w3d2.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;

public interface
PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> , PagingAndSortingRepository<Prenotazione,Integer> {
    boolean existsByDipendenteIdAndDataRichiesta(int dipendenteId, LocalDate dataRichiesta);
}
