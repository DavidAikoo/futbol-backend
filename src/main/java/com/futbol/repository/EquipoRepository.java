package com.futbol.repository;

import com.futbol.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    /**
     * Suma total de goles (como local + como visitante) de un equipo en todos sus partidos.
     */
    @Query(value = """
            SELECT COALESCE(SUM(g.total), 0)
            FROM (
                SELECT goles_local  AS total FROM partido WHERE equipo_local  = :idEquipo
                UNION ALL
                SELECT goles_visita AS total FROM partido WHERE equipo_visita = :idEquipo
            ) g
            """, nativeQuery = true)
    Long getTotalGolesByEquipo(@Param("idEquipo") Long idEquipo);
}
