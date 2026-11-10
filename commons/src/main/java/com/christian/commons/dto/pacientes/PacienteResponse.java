package com.christian.commons.dto.pacientes;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un paciente")
public record PacienteResponse(
        @Schema(description = "ID del paciente", example = "1")
        Long id,

        @Schema(description = "Nombre completo del paciente", example = "Máximo Décimo Meridio")
        String nombre,

        @Schema(description = "Edad del paciente", example = "1")
        Short edad,

        @Schema(description = "Peso del paciente", example = "75.5")
        Double peso,

        @Schema(description = "Estatura del paciente", example = "1.72")
        Double estatura,

        @Schema(description = "IMC del paciente", example = "26.72")
        Double imc,

        @Schema(description = "Email del paciente", example = "correo@correo.com")
        String email,

        @Schema(description = "Teléfono del paciente", example = "5512345678")
        String telefono,

        @Schema(description = "Dirección del paciente", example = "Av. Mártires de la Nación 123")
        String direccion,

        @Schema(description = "Número de expediente del paciente", example = "5X5X7X2X7X2X7X0X4X")
        String numExpediente
) { }