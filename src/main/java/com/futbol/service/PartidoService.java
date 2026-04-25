package com.futbol.service;

import com.futbol.entity.Partido;
import com.futbol.repository.PartidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PartidoService {

    private final PartidoRepository partidoRepository;

    @Transactional(readOnly = true)
    public List<Partido> findAll() {
        return partidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Partido findById(Long id) {
        return partidoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Partido no encontrado con id: " + id));
    }

    public Partido save(Partido partido) {
        partido.setIdPartido(null); // ← CLAVE
        return partidoRepository.save(partido);
    }

    public Partido update(Long id, Partido actualizado) {
        Partido existente = findById(id);
        existente.setFecha(actualizado.getFecha());
        existente.setEstadio(actualizado.getEstadio());
        existente.setEquipoLocal(actualizado.getEquipoLocal());
        existente.setEquipoVisita(actualizado.getEquipoVisita());
        existente.setGolesLocal(actualizado.getGolesLocal());
        existente.setGolesVisita(actualizado.getGolesVisita());
        return partidoRepository.save(existente);
    }

    public void delete(Long id) {
        findById(id);
        partidoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getResultadosConNombres() {
        List<Object[]> rows = partidoRepository.getResultadosConNombres();
        return rows.stream().map(row -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("idPartido",    row[0]);
            map.put("fecha",        row[1]);
            map.put("estadio",      row[2]);
            map.put("equipoLocal",  row[3]);
            map.put("equipoVisita", row[4]);
            map.put("golesLocal",   row[5]);
            map.put("golesVisita",  row[6]);
            return map;
        }).toList();
    }
}