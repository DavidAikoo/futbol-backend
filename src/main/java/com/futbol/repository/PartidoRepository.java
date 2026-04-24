package com.futbol.repository;

import com.futbol.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    @Query("SELECT p FROM Partido p JOIN FETCH p.equipoLocal JOIN FETCH p.equipoVisita ORDER BY p.fecha DESC")
    List<Partido> findAll();

    @Query("SELECT p FROM Partido p JOIN FETCH p.equipoLocal JOIN FETCH p.equipoVisita WHERE p.idPartido = :id")
    Optional<Partido> findById(@Param("id") Long id);

    @Query(value = """
            SELECT
                p.id_partido,
                p.fecha,
                p.estadio,
                el.nombre  AS equipo_local,
                ev.nombre  AS equipo_visita,
                p.goles_local,
                p.goles_visita
            FROM partido p
            INNER JOIN equipo el ON el.id_equipo = p.equipo_local
            INNER JOIN equipo ev ON ev.id_equipo = p.equipo_visita
            ORDER BY p.fecha DESC
            """, nativeQuery = true)
    List<Object[]> getResultadosConNombres();
}