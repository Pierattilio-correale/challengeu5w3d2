package it.epicode.challengeu5w3d2.service;

import com.cloudinary.Cloudinary;
import it.epicode.challengeu5w3d2.dto.ViaggioDto;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.model.Stato;
import it.epicode.challengeu5w3d2.model.Viaggio;
import it.epicode.challengeu5w3d2.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private Cloudinary cloudinary;

    public Viaggio saveViaggio(ViaggioDto viaggioDto){
        Viaggio viaggio = new Viaggio();
        viaggio.setDataViaggio(viaggioDto.getDataViaggio());
        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setStatoViaggio(Stato.IN_PROGRAMMA);
        sendMail("orsoblack2@gmail.com");
        return viaggioRepository.save(viaggio);
    }

public List<Viaggio>getAllViaggi(){
       return viaggioRepository.findAll();
}

public Viaggio getViaggio(int id) throws NotFoundException {
        return viaggioRepository.findById(id).orElseThrow(()->new NotFoundException("il viaggio con id "+id+" non è stato trovato"));

}

public Viaggio aggiornaViaggio(int id, ViaggioDto viaggioDto) throws NotFoundException {
        Viaggio viaggioDaAggiornare= getViaggio(id);
        viaggioDaAggiornare.setDestinazione(viaggioDto.getDestinazione());
        viaggioDaAggiornare.setDataViaggio(viaggioDto.getDataViaggio());

        return viaggioRepository.save(viaggioDaAggiornare);

}
public void deleteViaggio(int id) throws NotFoundException {
        Viaggio viaggioDaCancellare = getViaggio(id);
        viaggioRepository.delete(viaggioDaCancellare);
}
    public Viaggio patchStatoViaggio(int id, Stato nuovoStato) throws NotFoundException {
        Viaggio viaggio = getViaggio(id);
        viaggio.setStatoViaggio(nuovoStato);
        return viaggioRepository.save(viaggio);
    }


    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione del viaggio");
        message.setText("Registrazione dell viaggio al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }
}
