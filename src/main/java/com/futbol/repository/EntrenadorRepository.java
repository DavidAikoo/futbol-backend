package com.futbol.repository;

import com.futbol.entity.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

    List<Entrenador> findByEquipoIdEquipo(Long idEquipo);
}
