package com.futbol.controller;

import com.futbol.entity.Jugador;
import com.futbol.service.JugadorService;
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
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
@Tag(name = "Jugadores", description = "Operaciones CRUD sobre jugadores y consultas estadísticas")
public class JugadorController {

    private final JugadorService jugadorService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todos los jugadores")
    @ApiResponse(responseCode = "200", description = "Lista de jugadores obtenida correctamente")
    public ResponseEntity<List<Jugador>> findAll() {
        return ResponseEntity.ok(jugadorService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener jugador por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Jugador encontrado"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(jugadorService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Registrar un nuevo jugador")
    @ApiResponse(responseCode = "201", description = "Jugador creado exitosamente")
    public ResponseEntity<Jugador> save(@Valid @RequestBody Jugador jugador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.save(jugador));
    }

    // ─── PUT ──────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un jugador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Jugador actualizado"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Jugador jugador) {
        try {
            return ResponseEntity.ok(jugadorService.update(id, jugador));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un jugador")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Jugador eliminado"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            jugadorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── CONSULTA NATIVA: JUGADORES POR EQUIPO ────────────────────────────────
    @GetMapping("/equipo/{idEquipo}")
    @Operation(
        summary     = "Jugadores de un equipo",
        description = "Devuelve todos los jugadores pertenecientes a un equipo específico"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de jugadores del equipo"),
        @ApiResponse(responseCode = "404", description = "No se encontraron jugadores")
    })
    public ResponseEntity<List<Jugador>> findByEquipo(
            @Parameter(description = "ID del equipo") @PathVariable Long idEquipo) {
        return ResponseEntity.ok(jugadorService.findByEquipoId(idEquipo));
    }

    // ─── CONSULTA NATIVA: JUGADORES CON MÁS GOLES ────────────────────────────
    @GetMapping("/goles")
    @Operation(
        summary     = "Jugadores con más goles que un mínimo",
        description = "Retorna jugadores cuyo total de goles en estadísticas supera el valor indicado"
    )
    @ApiResponse(responseCode = "200", description = "Lista de jugadores con sus goles totales")
    public ResponseEntity<List<Jugador>> findConMasGoles(
            @Parameter(description = "Mínimo de goles (exclusivo)")
            @RequestParam(defaultValue = "0") int min) {
        return ResponseEntity.ok(jugadorService.findJugadoresConMasGoles(min));
    }
}
