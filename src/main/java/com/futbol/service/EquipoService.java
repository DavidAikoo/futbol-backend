package com.futbol.service;

import com.futbol.entity.Equipo;
import com.futbol.repository.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipoService {

    private final EquipoRepository equipoRepository;

    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Equipo findById(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Equipo no encontrado con id: " + id));
    }

    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo update(Long id, Equipo equipoActualizado) {
        Equipo existente = findById(id);
        existente.setNombre(equipoActualizado.getNombre());
        existente.setCiudad(equipoActualizado.getCiudad());
        existente.setFundacion(equipoActualizado.getFundacion());
        return equipoRepository.save(existente);
    }

    public void delete(Long id) {
        findById(id); // Verifica existencia
        equipoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Long getTotalGolesByEquipo(Long idEquipo) {
        return equipoRepository.getTotalGolesByEquipo(idEquipo);
    }
}
