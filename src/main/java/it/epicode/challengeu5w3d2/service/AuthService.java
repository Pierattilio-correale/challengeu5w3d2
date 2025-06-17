package it.epicode.challengeu5w3d2.service;

import it.epicode.challengeu5w3d2.dto.LoginDto;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.model.User;
import it.epicode.challengeu5w3d2.repository.UserRepository;
import it.epicode.challengeu5w3d2.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    // 1 verificare che l'utente esista
    // 2 se l'utente non esiste lanciare una eccezione
    // 3 se l'utente esiste dovrà generare il TOKEN e inviarlo al CLIEN

@Autowired
    private UserRepository userRepository;
@Autowired
private JwtTool jwtTool;

public  String login(LoginDto loginDto) throws NotFoundException {
    User user =userRepository.findByUsernameAndEmail((loginDto.getUsername()) , loginDto.getEmail()).orElseThrow(()->new NotFoundException("l'utente con questo username/password non esiste"));
   if(loginDto.getPassword().equals(user.getPassword())){


       return jwtTool.createToken(user);
   }else{
       throw new NotFoundException("l'utente con questo username/password non esiste");
   }
}
}
