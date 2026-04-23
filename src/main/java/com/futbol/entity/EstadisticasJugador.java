package com.futbol.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "estadisticas_jugador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticasJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadistica")
    private Long idEstadistica;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference("jugador-estadisticas")      // ← rompe el ciclo con Jugador
    private Jugador jugador;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_partido", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference("partido-estadisticas")      // ← rompe el ciclo con Partido
    private Partido partido;

    @Column(name = "minutos_jugados", nullable = false)
    private Integer minutosJugados = 0;

    @Column(nullable = false)
    private Integer goles = 0;

    @Column(nullable = false)
    private Integer asistencias = 0;

    @Column(name = "tarjetas_amarillas", nullable = false)
    private Integer tarjetasAmarillas = 0;

    @Column(name = "tarjetas_rojas", nullable = false)
    private Integer tarjetasRojas = 0;
}