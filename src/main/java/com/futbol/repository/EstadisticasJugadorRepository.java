package com.futbol.repository;

import com.futbol.entity.EstadisticasJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadisticasJugadorRepository extends JpaRepository<EstadisticasJugador, Long> {

    List<EstadisticasJugador> findByJugadorIdJugador(Long idJugador);

    List<EstadisticasJugador> findByPartidoIdPartido(Long idPartido);
}
