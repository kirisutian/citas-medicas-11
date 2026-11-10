package com.christian.citas.repository;

import com.christian.citas.entity.Cita;
import com.christian.citas.enums.EstadoCita;
import com.christian.commons.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    /*
    * SELECT COUNT(*) FROM CITAS WHERE ID_MEDICO = ? AND
    *           ESTADO_REGISTRO = ? AND ESTADO_CITA IN(?);
    * */
    boolean existsByIdMedicoAndEstadoRegistroAndEstadoCitaIn(
            Long idMedico, EstadoRegistro estadoRegistro, List<EstadoCita> estadoCitas);
}
