package com.futbol.repository;

import com.futbol.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    @Query("SELECT j FROM Jugador j JOIN FETCH j.equipo ORDER BY j.idJugador")
    List<Jugador> findAll();

    @Query("SELECT j FROM Jugador j JOIN FETCH j.equipo WHERE j.idJugador = :id")
    Optional<Jugador> findById(@Param("id") Long id);

    // ── JOIN FETCH para evitar LazyInitializationException en serialización ──
    @Query("SELECT j FROM Jugador j JOIN FETCH j.equipo WHERE j.equipo.idEquipo = :idEquipo ORDER BY j.dorsal")
    List<Jugador> findByEquipoId(@Param("idEquipo") Long idEquipo);

    @Query("""
            SELECT j FROM Jugador j JOIN FETCH j.equipo
            WHERE j.idJugador IN (
                SELECT e.jugador.idJugador
                FROM EstadisticasJugador e
                GROUP BY e.jugador.idJugador
                HAVING SUM(e.goles) > :minGoles
            )
            ORDER BY j.idJugador
            """)
    List<Jugador> findJugadoresConMasGoles(@Param("minGoles") int minGoles);
}