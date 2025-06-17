package it.epicode.challengeu5w3d2.controller;

import it.epicode.challengeu5w3d2.dto.DipendenteDto;
import it.epicode.challengeu5w3d2.exception.AlreadyExistException;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.exception.ValidationException;
import it.epicode.challengeu5w3d2.model.Dipendente;
import it.epicode.challengeu5w3d2.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/dipendenti")

public class DipendenteController {
@Autowired
    private DipendenteService dipendenteService;

@PostMapping("")
@PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente creaDipendente(@RequestBody  @Validated  DipendenteDto dipendenteDto, BindingResult bindingResult) throws ValidationException, AlreadyExistException {
    if(bindingResult.hasErrors()){
        throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

    }
    return dipendenteService.saveDipendente(dipendenteDto);
}
@GetMapping("")
@PreAuthorize("hasAuthority('ADMIN')")
    public List<Dipendente> getAllDipendenti(){
    return  dipendenteService.trovaTuttiIDipendenti();
}
@GetMapping("/{id}")
@PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente getDipendente(@PathVariable  int id) throws NotFoundException {
    return  dipendenteService.getDipendente(id);
}
@PutMapping("/{id}")
@PreAuthorize("hasAuthority('ADMIN')")
public Dipendente aggiornaDipendente(@PathVariable int id ,@RequestBody @Validated DipendenteDto dipendenteDto,BindingResult bindingResult) throws ValidationException, NotFoundException {
    if(bindingResult.hasErrors()){
        throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

    }
    return  dipendenteService.aggiornaDipendente(id,dipendenteDto);


}
@PatchMapping("/{id}")
@PreAuthorize("hasAuthority('ADMIN')")
public String patchImgDipendente(@PathVariable int id , @RequestBody MultipartFile file) throws NotFoundException, IOException {
return dipendenteService.patchDipendente(id,file);
}
@DeleteMapping("/{id}")
@PreAuthorize("hasAuthority('ADMIN')")
public void deleDipendente(@PathVariable int id) throws NotFoundException {
    dipendenteService.deleteDipendente(id);
}
}
