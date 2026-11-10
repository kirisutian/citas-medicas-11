package com.christian.commons.dto.pacientes;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de un paciente asociado a una cita")
public record DatosPaciente(

        @Schema(description = "Nombre completo del paciente", example = "Christian García López")
        String nombre,

        @Schema(description = "Número de expediente del paciente", example = "1X2X3X4X5X6X7X8X9X0X")
        String numExpediente,

        @Schema(description = "Edad del paciente", example = "18 años")
        String edad,

        @Schema(description = "Peso del paciente", example = "80.5 kg.")
        String peso,

        @Schema(description = "Estatura del paciente", example = "1.80 m.")
        String estatura,

        @Schema(description = "IMC del paciente", example = "22.2 Peso normal")
        String imc,

        @Schema(description = "Teléfono del paciente", example = "1234567890")
        String telefono
) {}
