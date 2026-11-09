package com.christian.commons.dto.medicos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un médico")
public record MedicoResponse(

        @Schema(description = "Identificador único del médico", example = "1")
        Long id,

        @Schema(description = "Nombre completo del médico", example = "Christian García López")
        String nombre,

        @Schema(description = "Edad del médico", example = "35")
        Short edad,

        @Schema(description = "Correo electrónico del médico", example = "christian.garcia@example.com")
        String email,

        @Schema(description = "Número telefónico de contacto del médico", example = "2221234567")
        String telefono,

        @Schema(description = "Cédula profesional del médico", example = "123456789012")
        String cedulaProfesional,

        @Schema(description = "Nombre de la especialidad médica del médico", example = "Cardiología")
        String especialidad,

        @Schema(description = "Descripción de la disponibilidad del médico",
                example = "Disponible para atender pacientes")
        String disponibilidad,

        @Schema(description = "Identificador de la disponibilidad asociada al médico", example = "1")
        Long idDisponibilidad
) {}