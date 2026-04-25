package com.futbol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EntrenadorRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String especialidad;

    private String fotoUrl;

    @NotNull
    private EquipoRef equipo;

    @Data
    public static class EquipoRef {
        @NotNull
        private Long idEquipo;
    }
}