package com.futbol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JugadorRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String posicion;

    @NotNull
    private Integer dorsal;

    @NotNull
    private LocalDate fechaNac;

    @NotBlank
    private String nacionalidad;

    private String fotoUrl;

    @NotNull
    private EquipoRef equipo;

    @Data
    public static class EquipoRef {
        @NotNull
        private Long idEquipo;
    }
}