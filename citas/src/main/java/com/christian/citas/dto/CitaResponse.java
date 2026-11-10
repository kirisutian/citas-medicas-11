package com.christian.citas.dto;

import com.christian.commons.dto.medicos.DatosMedico;
import com.christian.commons.dto.pacientes.DatosPaciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Información de una cita médica registrada")
public record CitaResponse(

        @Schema(description = "Identificador único de la cita", example = "1")
        Long id,

        @Schema(description = "Información del paciente asociado a la cita")
        DatosPaciente paciente,

        @Schema(description = "Información del médico que atenderá la cita")
        DatosMedico medico,

        @Schema(description = "Fecha y hora programada para la cita", example = "25/09/2026 10:30",
                type = "string",
                format = "date-time"
        )
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaCita,

        @Schema(
                description = "Descripción de los síntomas indicados por el paciente",
                example = "El paciente presenta dolor de cabeza intenso y fiebre desde hace dos días"
        )
        String sintomas,
        @Schema(description = "Estado actual de la cita", example = "Confirmada por el paciente")
        String estadoCita
) {}
