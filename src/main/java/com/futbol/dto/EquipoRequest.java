package com.futbol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipoRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String ciudad;

    @NotNull
    private LocalDate fundacion;

    private String fotoUrl;
}