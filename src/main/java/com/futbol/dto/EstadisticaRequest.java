package com.futbol.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadisticaRequest {

    @NotNull
    private JugadorRef jugador;

    @NotNull
    private PartidoRef partido;

    @NotNull
    private Integer minutosJugados;

    @NotNull
    private Integer goles;

    @NotNull
    private Integer asistencias;

    @NotNull
    private Integer tarjetasAmarillas;

    @NotNull
    private Integer tarjetasRojas;

    @Data
    public static class JugadorRef {
        @NotNull
        private Long idJugador;
    }

    @Data
    public static class PartidoRef {
        @NotNull
        private Long idPartido;
    }
}