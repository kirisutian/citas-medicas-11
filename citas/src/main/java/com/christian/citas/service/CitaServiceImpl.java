package com.christian.citas.service;

import com.christian.citas.dto.CitaRequest;
import com.christian.citas.dto.CitaResponse;
import com.christian.citas.entity.Cita;
import com.christian.citas.enums.EstadoCita;
import com.christian.citas.mapper.CitaMapper;
import com.christian.citas.repository.CitaRepository;
import com.christian.commons.enums.EstadoRegistro;
import com.christian.commons.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    private final CitaMapper citaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponse> listar() {

        log.info("Listando todas las citas activas...");

        return citaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(citaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponse obtenerPorId(Long id) {
        return citaMapper.entidadAResponse(obtenerCitaOException(id));
    }

    @Override
    public CitaResponse registrar(CitaRequest request) {

        log.info("Registrando nueva cita...");

        Cita cita = citaMapper.requestAEntidad(request);

        citaRepository.saveAndFlush(cita);

        log.info("Cita registrada");

        return citaMapper.entidadAResponse(cita);
    }

    @Override
    public CitaResponse actualizar(CitaRequest request, Long id) {

        Cita cita = obtenerCitaOException(id);

        log.info("Actualizando cita con id: {}", id);

        cita.actualizar(
                request.idPaciente(),
                request.idMedico(),
                request.fechaCita(),
                request.sintomas());

        citaRepository.saveAndFlush(cita);

        log.info("Cita actualizada");

        return citaMapper.entidadAResponse(cita);
    }

    @Override
    public void actualizarEstadoCita(Long idCita, Long idEstadoCita) {

        Cita cita = obtenerCitaOException(idCita);

        log.info("Actualizando estado de la cita con id: {}", idCita);

        cita.actualizarEstadoCita(
                EstadoCita.obtenerEstadoCitaPorCodigo(idEstadoCita));

        citaRepository.saveAndFlush(cita);

        log.info("Estado de la cita {} actualizado", idCita);
    }

    @Override
    public void eliminar(Long id) {

        Cita cita = obtenerCitaOException(id);

        log.info("Eliminando cita con id: {}", id);

        cita.eliminar();

        log.info("Cita con id {} ha sido marcada como eliminada", id);
    }

    private Cita obtenerCitaOException(Long id) {

        log.info("Buscando cita con id: {}", id);

        return citaRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Cita no encontrada con id: " + id));
    }
}
