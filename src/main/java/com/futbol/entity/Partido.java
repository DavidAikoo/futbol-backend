package com.futbol.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partido")
    private Long idPartido;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String estadio;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_local", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"jugadores", "entrenadores", "partidosComoLocal", "partidosComoVisita"})
    private Equipo equipoLocal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_visita", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"jugadores", "entrenadores", "partidosComoLocal", "partidosComoVisita"})
    private Equipo equipoVisita;

    @Column(name = "goles_local", nullable = false)
    private Integer golesLocal = 0;

    @Column(name = "goles_visita", nullable = false)
    private Integer golesVisita = 0;

    @OneToMany(mappedBy = "partido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<EstadisticasJugador> estadisticas;
}