package com.futbol.repository;

import com.futbol.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    /**
     * Obtiene todos los jugadores de un equipo específico.
     */
    @Query(value = """
            SELECT * FROM jugador
            WHERE id_equipo = :idEquipo
            ORDER BY dorsal
            """, nativeQuery = true)
    List<Jugador> findByEquipoId(@Param("idEquipo") Long idEquipo);

    /**
     * Jugadores cuyo total de goles acumulados en estadísticas supera el mínimo indicado.
     */
    @Query(value = """
            SELECT j.*
            FROM jugador j
            INNER JOIN estadisticas_jugador e ON e.id_jugador = j.id_jugador
            GROUP BY j.id_jugador, j.nombre, j.posicion, j.dorsal,
                     j.fecha_nac, j.nacionalidad, j.id_equipo
            HAVING SUM(e.goles) > :minGoles
            ORDER BY SUM(e.goles) DESC
            """, nativeQuery = true)
    List<Jugador> findJugadoresConMasGoles(@Param("minGoles") int minGoles);
}
