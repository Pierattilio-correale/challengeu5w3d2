package it.epicode.challengeu5w3d2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PrenotazioneDto {
    @NotNull(message = "inserire data richiesta!")
    private LocalDate dataRichiesta;

    private String note;
    private int dipendenteId;
    private int viaggioId;
}
