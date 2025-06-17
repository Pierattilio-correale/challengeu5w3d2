package it.epicode.challengeu5w3d2.controller;

import it.epicode.challengeu5w3d2.dto.UserDto;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.exception.ValidationException;
import it.epicode.challengeu5w3d2.model.User;
import it.epicode.challengeu5w3d2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUser(){
        return  userService.getAllUser();
    }
    @GetMapping("users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUser(@PathVariable int id) throws NotFoundException {
        return  userService.getUser(id);
    }
    @PutMapping("users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User aggiornaUser(@PathVariable int id , @RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }
        return  userService.updateUser(id,userDto);


    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable int id) throws NotFoundException {
        userService.deleteUser(id);
    }
}

