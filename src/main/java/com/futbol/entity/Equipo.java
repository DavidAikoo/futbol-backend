package com.futbol.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "equipo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Long idEquipo;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String ciudad;

    @NotNull
    @Column(nullable = false)
    private LocalDate fundacion;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Jugador> jugadores;

    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Entrenador> entrenadores;

    @OneToMany(mappedBy = "equipoLocal", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Partido> partidosComoLocal;

    @OneToMany(mappedBy = "equipoVisita", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Partido> partidosComoVisita;
}