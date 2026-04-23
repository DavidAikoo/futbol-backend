package com.futbol.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "entrenador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrenador")
    private Long idEntrenador;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties({"jugadores", "entrenadores", "partidosComoLocal", "partidosComoVisita"})
    private Equipo equipo;
}