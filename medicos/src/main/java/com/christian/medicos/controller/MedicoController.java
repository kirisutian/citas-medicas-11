package com.christian.medicos.controller;

import com.christian.commons.controller.CrudController;
import com.christian.commons.docs.ProblemaDoc;
import com.christian.commons.dto.medicos.MedicoRequest;
import com.christian.commons.dto.medicos.MedicoResponse;
import com.christian.medicos.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "API Médicos", description = "Métodos para la gestión de médicos")
public class MedicoController extends CrudController<MedicoRequest, MedicoResponse, MedicoService> {

    public MedicoController(MedicoService service) {
        super(service);
    }

    @GetMapping("/id-medico/{id}")
    @Operation(summary = "Obtener médico por id sin importar el estado del registro")
    @ApiResponse(responseCode = "200", description = "Médico sin estado encontrado")
    @ApiResponse(responseCode = "404", description = "El médico sin estado no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<MedicoResponse> obtenerMedicoPorIdSinEstado(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(service.obtenerMedicoPorIdSinEstado(id));
    }

    @PutMapping("/{idMedico}/disponibilidad/{idDisponibilidad}")
    @Operation(summary = "Actualizar disponibilidad del médico" +
            "(no es endpoint libre, debe gestionarlo el sistema de Citas)")
    @ApiResponse(responseCode = "204", description = "Disponibilidad del médico actualizada")
    @ApiResponse(responseCode = "409", description = "La disponibilidad del médico no puede " +
            "actualizarse por regla de negocio",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<Void> actualizarDisponibilidadMedico(
            @PathVariable @Positive(message = "El idMedico debe ser positivo") Long idMedico,
            @PathVariable @Positive(message = "El idDisponibilidad debe ser positivo") Long idDisponibilidad
    ) {
        service.actualizarDisponibilidadMedico(idMedico, idDisponibilidad);
        return ResponseEntity.noContent().build();
    }
}
