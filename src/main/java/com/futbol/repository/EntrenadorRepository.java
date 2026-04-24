package com.futbol.repository;

import com.futbol.entity.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

    @Query("SELECT e FROM Entrenador e JOIN FETCH e.equipo ORDER BY e.idEntrenador")
    List<Entrenador> findAll();

    @Query("SELECT e FROM Entrenador e JOIN FETCH e.equipo WHERE e.idEntrenador = :id")
    Optional<Entrenador> findById(@Param("id") Long id);

    @Query("SELECT e FROM Entrenador e JOIN FETCH e.equipo WHERE e.equipo.idEquipo = :idEquipo")
    List<Entrenador> findByEquipoIdEquipo(@Param("idEquipo") Long idEquipo);
}