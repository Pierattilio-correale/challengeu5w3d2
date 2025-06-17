package it.epicode.challengeu5w3d2.repository;

import it.epicode.challengeu5w3d2.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DipendenteRepository extends JpaRepository<Dipendente,Integer>, PagingAndSortingRepository<Dipendente,Integer> {
    boolean existsByUserName(String userName);
}
