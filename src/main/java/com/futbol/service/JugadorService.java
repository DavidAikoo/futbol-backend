package com.futbol.service;

import com.futbol.entity.Jugador;
import com.futbol.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    @Transactional(readOnly = true)
    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Jugador findById(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Jugador no encontrado con id: " + id));
    }

    public Jugador save(Jugador jugador) {
        jugador.setIdJugador(null); // ← CLAVE
        return jugadorRepository.save(jugador);
    }

    public Jugador update(Long id, Jugador actualizado) {
        Jugador existente = findById(id);
        existente.setNombre(actualizado.getNombre());
        existente.setPosicion(actualizado.getPosicion());
        existente.setDorsal(actualizado.getDorsal());
        existente.setFechaNac(actualizado.getFechaNac());
        existente.setNacionalidad(actualizado.getNacionalidad());
        existente.setEquipo(actualizado.getEquipo());
        return jugadorRepository.save(existente);
    }

    public void delete(Long id) {
        findById(id);
        jugadorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Jugador> findByEquipoId(Long idEquipo) {
        return jugadorRepository.findByEquipoId(idEquipo);
    }

    @Transactional(readOnly = true)
    public List<Jugador> findJugadoresConMasGoles(int minGoles) {
        return jugadorRepository.findJugadoresConMasGoles(minGoles);
    }
}