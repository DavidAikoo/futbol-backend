package com.futbol.controller;

import com.futbol.dto.EquipoRequest;
import com.futbol.entity.Equipo;
import com.futbol.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
@Tag(name = "Equipos", description = "Operaciones CRUD sobre equipos de fútbol")
public class EquipoController {

    private final EquipoService equipoService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todos los equipos")
    @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida correctamente")
    public ResponseEntity<List<Equipo>> findAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener equipo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del equipo") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(equipoService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Crear un nuevo equipo")
    @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente")
    public ResponseEntity<Equipo> save(@Valid @RequestBody EquipoRequest request) {
        Equipo equipo = new Equipo();
        equipo.setNombre(request.getNombre());
        equipo.setCiudad(request.getCiudad());
        equipo.setFundacion(request.getFundacion());
        equipo.setFotoUrl(request.getFotoUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.save(equipo));
    }

    // ─── PUT ──────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un equipo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo actualizado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequest request) {
        try {
            Equipo equipo = new Equipo();
            equipo.setNombre(request.getNombre());
            equipo.setCiudad(request.getCiudad());
            equipo.setFundacion(request.getFundacion());
            equipo.setFotoUrl(request.getFotoUrl());
            return ResponseEntity.ok(equipoService.update(id, equipo));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipo eliminado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado"),
            @ApiResponse(responseCode = "409", description = "El equipo tiene jugadores o partidos asociados")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            equipoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar: el equipo tiene jugadores, entrenadores o partidos asociados.");
        }
    }

    // ─── GOLES TOTALES ────────────────────────────────────────────────────────
    @GetMapping("/{idEquipo}/goles-totales")
    @Operation(summary = "Total de goles de un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total de goles calculado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    public ResponseEntity<?> getTotalGoles(
            @Parameter(description = "ID del equipo") @PathVariable Long idEquipo) {
        try {
            equipoService.findById(idEquipo);
            Long total = equipoService.getTotalGolesByEquipo(idEquipo);
            return ResponseEntity.ok(Map.of("idEquipo", idEquipo, "totalGoles", total));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}