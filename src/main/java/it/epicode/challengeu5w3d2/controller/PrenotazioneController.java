package it.epicode.challengeu5w3d2.controller;

import it.epicode.challengeu5w3d2.dto.PrenotazioneDto;
import it.epicode.challengeu5w3d2.exception.AlreadyExistException;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.exception.ValidationException;
import it.epicode.challengeu5w3d2.model.Prenotazione;
import it.epicode.challengeu5w3d2.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
@PreAuthorize("hasAuthority('ADMIN')")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("")

    public Prenotazione creaPrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto,
                                         BindingResult bindingResult)
            throws ValidationException, AlreadyExistException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }

        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    @GetMapping("")

    public Page<Prenotazione> getAllPrenotazioni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return prenotazioneService.getAllPrenotazioni(page, size, sortBy);
    }

    @GetMapping("/{id}")

    public Prenotazione getPrenotazione(@PathVariable int id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    @PutMapping("/{id}")

    public Prenotazione aggiornaPrenotazione(@PathVariable int id,
                                             @RequestBody @Validated PrenotazioneDto prenotazioneDto,
                                             BindingResult bindingResult)
            throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));

        }


        return prenotazioneService.aggiornaPrenotazione(id, prenotazioneDto);
    }

    @DeleteMapping("/{id}")
   
    public void eliminaPrenotazione(@PathVariable int id) throws NotFoundException {
        prenotazioneService.deletePrenotazione(id);
    }
}

