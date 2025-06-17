package it.epicode.challengeu5w3d2.controller;

import it.epicode.challengeu5w3d2.dto.LoginDto;
import it.epicode.challengeu5w3d2.dto.UserDto;
import it.epicode.challengeu5w3d2.exception.AlreadyExistException;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.exception.ValidationException;
import it.epicode.challengeu5w3d2.model.User;
import it.epicode.challengeu5w3d2.service.AuthService;
import it.epicode.challengeu5w3d2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody  @Validated  UserDto userDto, BindingResult bindingResult) throws ValidationException, AlreadyExistException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s,e)->s+e));
        }
      return  userService.saveUser(userDto);
    }

    @GetMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s,e)->s+e));
        }


return authService.login(loginDto);
    }
}
