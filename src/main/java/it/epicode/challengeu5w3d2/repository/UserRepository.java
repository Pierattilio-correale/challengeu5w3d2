package it.epicode.challengeu5w3d2.repository;

import it.epicode.challengeu5w3d2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User>findByUsernameAndEmail(String username,String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
