package com.futbol.controller;

import com.futbol.dto.EntrenadorRequest;
import com.futbol.entity.Entrenador;
import com.futbol.entity.Equipo;
import com.futbol.service.EntrenadorService;
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
@RequestMapping("/api/entrenadores")
@RequiredArgsConstructor
@Tag(name = "Entrenadores", description = "Operaciones CRUD sobre entrenadores del equipo")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    // ─── GET ALL ──────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todos los entrenadores")
    @ApiResponse(responseCode = "200", description = "Lista de entrenadores obtenida correctamente")
    public ResponseEntity<List<Entrenador>> findAll() {
        return ResponseEntity.ok(entrenadorService.findAll());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener entrenador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrenador encontrado"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    })
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(entrenadorService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POST ─────────────────────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Registrar un nuevo entrenador")
    @ApiResponse(responseCode = "201", description = "Entrenador creado exitosamente")
    public ResponseEntity<Entrenador> save(@Valid @RequestBody EntrenadorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(entrenadorService.save(toEntity(request)));
    }

    // ─── PUT ──────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un entrenador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrenador actualizado"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    })
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody EntrenadorRequest request) {
        try {
            return ResponseEntity.ok(entrenadorService.update(id, toEntity(request)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un entrenador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entrenador eliminado"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            entrenadorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── POR EQUIPO ───────────────────────────────────────────────────────────
    @GetMapping("/equipo/{idEquipo}")
    @Operation(summary = "Entrenadores de un equipo específico")
    @ApiResponse(responseCode = "200", description = "Lista de entrenadores del equipo")
    public ResponseEntity<List<Entrenador>> findByEquipo(
            @Parameter(description = "ID del equipo") @PathVariable Long idEquipo) {
        return ResponseEntity.ok(entrenadorService.findByEquipoId(idEquipo));
    }

    // ─── HELPER ───────────────────────────────────────────────────────────────
    private Entrenador toEntity(EntrenadorRequest req) {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(req.getEquipo().getIdEquipo());

        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(req.getNombre());
        entrenador.setEspecialidad(req.getEspecialidad());
        entrenador.setEquipo(equipo);
        return entrenador;
    }
}