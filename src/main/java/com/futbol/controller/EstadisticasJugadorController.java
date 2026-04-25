package com.futbol.controller;

import com.futbol.dto.EstadisticaRequest;
import com.futbol.entity.EstadisticasJugador;
import com.futbol.entity.Jugador;
import com.futbol.entity.Partido;
import com.futbol.service.EstadisticasJugadorService;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/estadisticas")
@RequiredArgsConstructor
@Tag(name = "Estadísticas de Jugadores", description = "Operaciones CRUD sobre estadísticas por partido")
public class EstadisticasJugadorController {

    private final EstadisticasJugadorService estadisticasService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todas las estadísticas")
    @ApiResponse(responseCode = "200", description = "Lista de estadísticas obtenida correctamente")
    public ResponseEntity<List<EstadisticasJugador>> findAll() {
        return ResponseEntity.ok(estadisticasService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener estadística por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estadística encontrada"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(estadisticasService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Registrar estadísticas de un jugador en un partido")
    @ApiResponse(responseCode = "201", description = "Estadística creada exitosamente")
    public ResponseEntity<EstadisticasJugador> save(
            @Valid @RequestBody EstadisticaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estadisticasService.save(toEntity(request)));
    }

    // ─── PUT ──────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estadística existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estadística actualizada"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody EstadisticaRequest request) {
        try {
            return ResponseEntity.ok(estadisticasService.update(id, toEntity(request)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una estadística")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estadística eliminada"),
            @ApiResponse(responseCode = "404", description = "Estadística no encontrada")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            estadisticasService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POR JUGADOR ──────────────────────────────────────────────────────────
    @GetMapping("/jugador/{idJugador}")
    @Operation(summary = "Estadísticas de un jugador en todos sus partidos")
    @ApiResponse(responseCode = "200", description = "Lista de estadísticas del jugador")
    public ResponseEntity<List<EstadisticasJugador>> findByJugador(
            @Parameter(description = "ID del jugador") @PathVariable Long idJugador) {
        return ResponseEntity.ok(estadisticasService.findByJugador(idJugador));
    }

    // ─── POR PARTIDO ──────────────────────────────────────────────────────────
    @GetMapping("/partido/{idPartido}")
    @Operation(summary = "Estadísticas de todos los jugadores en un partido")
    @ApiResponse(responseCode = "200", description = "Lista de estadísticas del partido")
    public ResponseEntity<List<EstadisticasJugador>> findByPartido(
            @Parameter(description = "ID del partido") @PathVariable Long idPartido) {
        return ResponseEntity.ok(estadisticasService.findByPartido(idPartido));
    }

    // ─── HELPER ───────────────────────────────────────────────────────────────
    private EstadisticasJugador toEntity(EstadisticaRequest req) {
        Jugador jugador = new Jugador();
        jugador.setIdJugador(req.getJugador().getIdJugador());

        Partido partido = new Partido();
        partido.setIdPartido(req.getPartido().getIdPartido());

        EstadisticasJugador est = new EstadisticasJugador();
        est.setJugador(jugador);
        est.setPartido(partido);
        est.setMinutosJugados(req.getMinutosJugados());
        est.setGoles(req.getGoles());
        est.setAsistencias(req.getAsistencias());
        est.setTarjetasAmarillas(req.getTarjetasAmarillas());
        est.setTarjetasRojas(req.getTarjetasRojas());
        return est;
    }
}