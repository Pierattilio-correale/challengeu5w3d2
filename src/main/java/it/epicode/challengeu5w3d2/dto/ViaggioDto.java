package it.epicode.challengeu5w3d2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ViaggioDto {
    @NotEmpty(message = "la destinazione non può essere nulla o vuota")
    private String destinazione;
    @NotNull(message = "la data non può essere nulla!")
    private LocalDate dataViaggio;


}
