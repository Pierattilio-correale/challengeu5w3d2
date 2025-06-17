package it.epicode.challengeu5w3d2.service;

import com.cloudinary.Cloudinary;
import it.epicode.challengeu5w3d2.dto.DipendenteDto;
import it.epicode.challengeu5w3d2.exception.AlreadyExistException;
import it.epicode.challengeu5w3d2.exception.NotFoundException;
import it.epicode.challengeu5w3d2.model.Dipendente;
import it.epicode.challengeu5w3d2.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private Cloudinary cloudinary;

    public Dipendente saveDipendente(DipendenteDto dipendenteDto) throws AlreadyExistException {
        if(dipendenteRepository.existsByUserName(dipendenteDto.getUserName())) {
            throw new AlreadyExistException("Username già esistente");
        }
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setEmail(dipendenteDto.getEmail());
        dipendente.setUserName(dipendenteDto.getUserName());
        sendMail("orsoblack2@gmail.com");
        return dipendenteRepository.save(dipendente);
    }
    public List<Dipendente>trovaTuttiIDipendenti(){
        return  dipendenteRepository.findAll();
    }

    public Dipendente getDipendente(int id) throws NotFoundException{

        return dipendenteRepository.findById(id).orElseThrow(()->new NotFoundException("il dipendente con id "+id+" non è stato trovato"));
    }


    public Dipendente aggiornaDipendente(int id , DipendenteDto dipendenteDto) throws NotFoundException {
        Dipendente dipendenteDaAggiornare = getDipendente(id);
        dipendenteDaAggiornare.setUserName(dipendenteDto.getUserName());
        dipendenteDaAggiornare.setNome(dipendenteDto.getNome());
        dipendenteDaAggiornare.setCognome(dipendenteDto.getCognome());
        dipendenteDaAggiornare.setEmail(dipendenteDto.getEmail());

        return dipendenteRepository.save(dipendenteDaAggiornare);

    }

    public void deleteDipendente(int id) throws NotFoundException{
Dipendente dipendenteDaEliminare = getDipendente(id);
dipendenteRepository.delete(dipendenteDaEliminare);

    }
    public String patchDipendente(int id, MultipartFile file) throws NotFoundException, IOException {
Dipendente dipendenteDaPatchare=getDipendente(id);
String url= (String)cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap()).get("url");

dipendenteDaPatchare.setImmagineProfilo(url);
dipendenteRepository.save(dipendenteDaPatchare);
return url;
    }
    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Dipendente");
        message.setText("Registrazione del Dipendente al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }
}
