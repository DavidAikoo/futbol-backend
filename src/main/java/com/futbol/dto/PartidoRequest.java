package com.futbol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PartidoRequest {

    @NotNull
    private LocalDate fecha;

    @NotBlank
    private String estadio;

    @NotNull
    private EquipoRef equipoLocal;

    @NotNull
    private EquipoRef equipoVisita;

    @NotNull
    private Integer golesLocal;

    @NotNull
    private Integer golesVisita;

    @Data
    public static class EquipoRef {
        @NotNull
        private Long idEquipo;
    }
}