package com.futbol.repository;

import com.futbol.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    /**
     * Retorna todos los partidos con los nombres de equipo local y visitante,
     * usando JOIN con la tabla equipo.
     * Projection: id_partido, fecha, estadio, local, visita, goles_local, goles_visita
     */
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
