package com.christian.citas.mapper;

import com.christian.citas.dto.CitaRequest;
import com.christian.citas.dto.CitaResponse;
import com.christian.citas.entity.Cita;
import com.christian.commons.dto.medicos.DatosMedico;
import com.christian.commons.dto.medicos.MedicoResponse;
import com.christian.commons.dto.pacientes.DatosPaciente;
import com.christian.commons.dto.pacientes.PacienteResponse;
import com.christian.commons.mapper.CommonMapper;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper implements CommonMapper<CitaRequest, CitaResponse, Cita> {

    @Override
    public Cita requestAEntidad(CitaRequest request) {
        return request == null
                ? null
                : Cita.crear(
                    request.idPaciente(),
                    request.idMedico(),
                    request.fechaCita(),
                    request.sintomas());
    }

    @Override
    public CitaResponse entidadAResponse(Cita entidad) {
        return entidad == null
                ? null
                : new CitaResponse(
                    entidad.getId(),
                    null,
                    null,
                    entidad.getFechaCita(),
                    entidad.getSintomas(),
                    entidad.getEstadoCita().getDescripcion()
        );
    }

    public CitaResponse entidadAResponse(Cita entidad, PacienteResponse paciente, MedicoResponse medico) {
        return entidad == null
                ? null
                : new CitaResponse(
                    entidad.getId(),
                    pacienteResponseADatosPaciente(paciente),
                    medicoResponseADatosMedico(medico),
                    entidad.getFechaCita(),
                    entidad.getSintomas(),
                    entidad.getEstadoCita().getDescripcion()
        );
    }

    private DatosPaciente pacienteResponseADatosPaciente(PacienteResponse paciente) {
        return paciente == null
                ? null
                : new DatosPaciente(
                paciente.nombre(),
                paciente.numExpediente(),
                paciente.edad() + " años",
                paciente.peso() + " kg.",
                paciente.estatura() + " m.",
                String.join(" ",
                        Math.round(paciente.imc() * 100.0) / 100.0 + "",
                        clasificacionIMC(paciente.imc())),
                paciente.telefono()
        );
    }

    private String clasificacionIMC(double imc) {
        if(imc < 18.5) return "Bajo peso";
        if(imc < 25) return "Peso normal";
        if(imc < 30) return "Sobrepeso";
        if(imc < 35) return "Obesidad grado I";
        if(imc < 40) return "Obesidad grado II";
        return "Obesidad grado III";
    }

    private DatosMedico medicoResponseADatosMedico(MedicoResponse medico) {

        return medico == null
                ? null
                : new DatosMedico(
                    medico.nombre(),
                    medico.cedulaProfesional(),
                    medico.especialidad());
    }
}
