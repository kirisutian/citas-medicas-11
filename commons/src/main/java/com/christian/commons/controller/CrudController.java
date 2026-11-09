package com.christian.commons.controller;

import com.christian.commons.docs.ProblemaDoc;
import com.christian.commons.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@ApiResponse(responseCode = "400", description = "Datos o parámetros inválidos",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
@ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
public class CrudController<RQ, RS, S extends CrudService<RQ, RS>> {

    protected final S service;

    @GetMapping
    @Operation(summary = "Listar registros")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<RS>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "El registro no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<RS> obtenerPorId(

            @Parameter(description = "Id del registro", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo recurso")
    @ApiResponse(responseCode = "201", description = "Registro creado")
    @ApiResponse(responseCode = "409", description = "Conflicto con los datos enviados",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<RS> registrar(
            @Valid @RequestBody RQ request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un registro existente")
    @ApiResponse(responseCode = "200", description = "Registro actualizado")
    @ApiResponse(responseCode = "404", description = "El registro no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    @ApiResponse(responseCode = "409", description = "Conflicto con los nuevos datos del registro",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<RS> actualizar(

            @Parameter(description = "Id del registro", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,

            @Valid @RequestBody RQ request
    ) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro")
    @ApiResponse(responseCode = "204", description = "Registro eliminado")
    @ApiResponse(responseCode = "404", description = "El registro no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    @ApiResponse(responseCode = "409", description = "El registro está en uso y no puede eliminarse",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id del registro", example = "1")
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
