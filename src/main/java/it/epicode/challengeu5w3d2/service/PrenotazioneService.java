package it.epicode.challengeu5w3d2.service;

import com.cloudinary.Cloudinary;
import it.epicode.challengeu5w3d2.dto.PrenotazioneDto;
import it.epicode.challengeu5w3d2.exception.AlreadyExistException;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.model.Dipendente;
import it.epicode.challengeu5w3d2.model.Prenotazione;
import it.epicode.challengeu5w3d2.model.Viaggio;
import it.epicode.challengeu5w3d2.repository.DipendenteRepository;
import it.epicode.challengeu5w3d2.repository.PrenotazioneRepository;
import it.epicode.challengeu5w3d2.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private ViaggioService viaggioService;

    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDtodto) throws NotFoundException, AlreadyExistException {
        Dipendente dipendente = dipendenteRepository.findById(prenotazioneDtodto.getDipendenteId())
                .orElseThrow(() -> new NotFoundException("Dipendente non trovato"));

        Viaggio viaggio = viaggioRepository.findById(prenotazioneDtodto.getViaggioId())
                .orElseThrow(() -> new NotFoundException("Viaggio non trovato"));


        boolean esiste = prenotazioneRepository.existsByDipendenteIdAndDataRichiesta(prenotazioneDtodto.getDipendenteId(), prenotazioneDtodto.getDataRichiesta());
        if (esiste) {
            throw new AlreadyExistException("Prenotazione già esistente per quel giorno");
        }

        Prenotazione p = new Prenotazione();
        p.setDipendente(dipendente);
        p.setViaggio(viaggio);
        p.setDataRichiesta(prenotazioneDtodto.getDataRichiesta());
        p.setNote(prenotazioneDtodto.getNote());
sendMail("orsoblack2@gmail.com");
        return prenotazioneRepository.save(p);
    }


public Page<Prenotazione> getAllPrenotazioni(int page , int size , String sortBy){
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return  prenotazioneRepository.findAll(pageable);

}

public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(()->new NotFoundException("la prenotazione con id "+id+" non è stata trovata"));

}

public Prenotazione aggiornaPrenotazione(int id , PrenotazioneDto prenotazioneDto) throws NotFoundException {
        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);

        prenotazioneDaAggiornare.setNote(prenotazioneDto.getNote());
        prenotazioneDaAggiornare.setDataRichiesta(prenotazioneDto.getDataRichiesta());
    if(prenotazioneDaAggiornare.getDipendente().getId()!= prenotazioneDto.getDipendenteId()){
       Dipendente dipendente= dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
        prenotazioneDaAggiornare.setDipendente(dipendente);
    }
    if(prenotazioneDaAggiornare.getViaggio().getId()!= prenotazioneDto.getViaggioId()){
       Viaggio viaggio= viaggioService.getViaggio(prenotazioneDto.getViaggioId());
        prenotazioneDaAggiornare.setViaggio(viaggio);
    }
    return prenotazioneRepository.save(prenotazioneDaAggiornare);
}

public void deletePrenotazione(int id) throws NotFoundException {
        Prenotazione prenotazioneDaCancellare = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneDaCancellare);
}
    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione della prenotazione");
        message.setText("Registrazione della prenotazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }
}
