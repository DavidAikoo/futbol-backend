package com.futbol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "jugador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    private Long idJugador;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String posicion;

    @NotNull
    @Column(nullable = false)
    private Integer dorsal;

    @NotNull
    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNac;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String nacionalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Equipo equipo;

    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EstadisticasJugador> estadisticas;
}
