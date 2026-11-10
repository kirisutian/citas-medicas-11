package com.christian.citas.service;

import com.christian.citas.dto.CitaRequest;
import com.christian.citas.dto.CitaResponse;
import com.christian.commons.service.CrudService;

public interface CitaService extends CrudService<CitaRequest, CitaResponse> {

    void actualizarEstadoCita(Long idCita, Long idEstadoCita);

    void medicoTieneCitasAsignadas(Long idMedico);
}