package com.futbol.service;

import com.futbol.entity.EstadisticasJugador;
import com.futbol.repository.EstadisticasJugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EstadisticasJugadorService {

    private final EstadisticasJugadorRepository estadisticasRepository;

    @Transactional(readOnly = true)
    public List<EstadisticasJugador> findAll() {
        return estadisticasRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EstadisticasJugador findById(Long id) {
        return estadisticasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estadística no encontrada con id: " + id));
    }

    public EstadisticasJugador save(EstadisticasJugador estadisticas) {
        estadisticas.setIdEstadistica(null); // ← CLAVE
        return estadisticasRepository.save(estadisticas);
    }

    public EstadisticasJugador update(Long id, EstadisticasJugador actualizado) {
        EstadisticasJugador existente = findById(id);
        existente.setJugador(actualizado.getJugador());
        existente.setPartido(actualizado.getPartido());
        existente.setMinutosJugados(actualizado.getMinutosJugados());
        existente.setGoles(actualizado.getGoles());
        existente.setAsistencias(actualizado.getAsistencias());
        existente.setTarjetasAmarillas(actualizado.getTarjetasAmarillas());
        existente.setTarjetasRojas(actualizado.getTarjetasRojas());
        return estadisticasRepository.save(existente);
    }

    public void delete(Long id) {
        findById(id);
        estadisticasRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<EstadisticasJugador> findByJugador(Long idJugador) {
        return estadisticasRepository.findByJugadorIdJugador(idJugador);
    }

    @Transactional(readOnly = true)
    public List<EstadisticasJugador> findByPartido(Long idPartido) {
        return estadisticasRepository.findByPartidoIdPartido(idPartido);
    }
}