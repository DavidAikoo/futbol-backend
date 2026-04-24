package com.futbol.repository;

import com.futbol.entity.EstadisticasJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticasJugadorRepository extends JpaRepository<EstadisticasJugador, Long> {

    @Query("SELECT e FROM EstadisticasJugador e JOIN FETCH e.jugador j JOIN FETCH j.equipo JOIN FETCH e.partido p JOIN FETCH p.equipoLocal JOIN FETCH p.equipoVisita ORDER BY e.idEstadistica")
    List<EstadisticasJugador> findAll();

    @Query("SELECT e FROM EstadisticasJugador e JOIN FETCH e.jugador j JOIN FETCH j.equipo JOIN FETCH e.partido p JOIN FETCH p.equipoLocal JOIN FETCH p.equipoVisita WHERE e.idEstadistica = :id")
    Optional<EstadisticasJugador> findById(@Param("id") Long id);

    @Query("SELECT e FROM EstadisticasJugador e JOIN FETCH e.jugador j JOIN FETCH j.equipo JOIN FETCH e.partido p JOIN FETCH p.equipoLocal JOIN FETCH p.equipoVisita WHERE j.idJugador = :idJugador")
    List<EstadisticasJugador> findByJugadorIdJugador(@Param("idJugador") Long idJugador);

    @Query("SELECT e FROM EstadisticasJugador e JOIN FETCH e.jugador j JOIN FETCH j.equipo JOIN FETCH e.partido p JOIN FETCH p.equipoLocal JOIN FETCH p.equipoVisita WHERE p.idPartido = :idPartido")
    List<EstadisticasJugador> findByPartidoIdPartido(@Param("idPartido") Long idPartido);
}