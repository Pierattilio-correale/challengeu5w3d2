package it.epicode.challengeu5w3d2.service;

import it.epicode.challengeu5w3d2.dto.UserDto;
import it.epicode.challengeu5w3d2.enumerated.Role;
import it.epicode.challengeu5w3d2.exception.AlreadyExistException;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.model.User;
import it.epicode.challengeu5w3d2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

public User saveUser(UserDto userDto) throws AlreadyExistException {

    User user= new User();
    if(userRepository.existsByUsername(userDto.getUsername())) {
        throw new AlreadyExistException("Username già esistente");
    }
    if(userRepository.existsByEmail(userDto.getEmail())) {
        throw new AlreadyExistException("email già esistente");
    }
    user.setNome(userDto.getNome());
    user.setCognome(userDto.getCognome());
    user.setUsername(userDto.getUsername());
    user.setPassword(userDto.getPassword());
    user.setEmail(userDto.getEmail());
    user.setRole(Role.USER);

    return userRepository.save(user);

}

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUser(int id)throws NotFoundException {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("L'utente con ID " + id + " non è stato trovato"));
    }


    public User updateUser(int id , UserDto userDto) throws NotFoundException{
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setPassword(userDto.getPassword());
        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setUsername(userDto.getUsername());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setEmail(userDto.getEmail());


        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id)throws NotFoundException{
        User userDaEliminare = getUser(id);
        userRepository.delete(userDaEliminare);
    }
}
