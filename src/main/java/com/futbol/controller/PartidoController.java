package com.futbol.controller;

import com.futbol.dto.PartidoRequest;
import com.futbol.entity.Equipo;
import com.futbol.entity.Partido;
import com.futbol.service.PartidoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
@Tag(name = "Partidos", description = "Operaciones CRUD sobre partidos y resultados")
public class PartidoController {

    private final PartidoService partidoService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todos los partidos")
    @ApiResponse(responseCode = "200", description = "Lista de partidos obtenida correctamente")
    public ResponseEntity<List<Partido>> findAll() {
        return ResponseEntity.ok(partidoService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener partido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partido encontrado"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(partidoService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Registrar un nuevo partido")
    @ApiResponse(responseCode = "201", description = "Partido creado exitosamente")
    public ResponseEntity<Partido> save(@Valid @RequestBody PartidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(partidoService.save(toEntity(request)));
    }

    // ─── PUT ──────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un partido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partido actualizado"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody PartidoRequest request) {
        try {
            return ResponseEntity.ok(partidoService.update(id, toEntity(request)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un partido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Partido eliminado"),
            @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            partidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── RESULTADOS CON NOMBRES ───────────────────────────────────────────────
    @GetMapping("/resultados")
    @Operation(summary = "Resultados de todos los partidos con nombres de equipo")
    @ApiResponse(responseCode = "200", description = "Lista de resultados con nombres de equipos")
    public ResponseEntity<List<Map<String, Object>>> getResultadosConNombres() {
        return ResponseEntity.ok(partidoService.getResultadosConNombres());
    }

    // ─── HELPER ───────────────────────────────────────────────────────────────
    private Partido toEntity(PartidoRequest req) {
        Equipo local = new Equipo();
        local.setIdEquipo(req.getEquipoLocal().getIdEquipo());

        Equipo visita = new Equipo();
        visita.setIdEquipo(req.getEquipoVisita().getIdEquipo());

        Partido partido = new Partido();
        partido.setFecha(req.getFecha());
        partido.setEstadio(req.getEstadio());
        partido.setEquipoLocal(local);
        partido.setEquipoVisita(visita);
        partido.setGolesLocal(req.getGolesLocal());
        partido.setGolesVisita(req.getGolesVisita());
        return partido;
    }
}