package com.futbol.service;

import com.futbol.entity.Entrenador;
import com.futbol.repository.EntrenadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;

    @Transactional(readOnly = true)
    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Entrenador findById(Long id) {
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entrenador no encontrado con id: " + id));
    }

    public Entrenador save(Entrenador entrenador) {
        entrenador.setIdEntrenador(null); // ← CLAVE
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador update(Long id, Entrenador actualizado) {
        Entrenador existente = findById(id);
        existente.setNombre(actualizado.getNombre());
        existente.setEspecialidad(actualizado.getEspecialidad());
        existente.setFotoUrl(actualizado.getFotoUrl());
        existente.setEquipo(actualizado.getEquipo());
        return entrenadorRepository.save(existente);
    }

    public void delete(Long id) {
        findById(id);
        entrenadorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Entrenador> findByEquipoId(Long idEquipo) {
        return entrenadorRepository.findByEquipoIdEquipo(idEquipo);
    }
}