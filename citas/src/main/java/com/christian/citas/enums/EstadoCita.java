package com.christian.citas.enums;

import com.christian.commons.exceptions.DatoInvalidoException;
import com.christian.commons.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum EstadoCita {

    PENDIENTE(1L,"Pendiente de confirmar",true,true) {
        @Override
        public Set<EstadoCita> puedeCambiar() {
            return EnumSet.of(CONFIRMADA, CANCELADA);
        }
    },

    CONFIRMADA(2L,"Confirmada por el paciente",true,false) {
        @Override
        public Set<EstadoCita> puedeCambiar() {
            return EnumSet.of(EN_CURSO, CANCELADA);
        }
    },

    EN_CURSO(3L,"Paciente llegó a su cita",true,false) {
        @Override
        public Set<EstadoCita> puedeCambiar() {
            return EnumSet.of(FINALIZADA);
        }
    },

    FINALIZADA(4L,"Cita finalizada",false,true) {
        @Override
        public Set<EstadoCita> puedeCambiar() {
            return Set.of();
        }
    },

    CANCELADA(5L,"Cita cancelada",false,true) {
        @Override
        public Set<EstadoCita> puedeCambiar() {
            return Set.of();
        }
    };

    private final Long codigo;

    private final String descripcion;

    private final boolean actualizable;

    private final boolean eliminable;

    public abstract Set<EstadoCita> puedeCambiar();

    public boolean puedeCambiarA(EstadoCita estadoCita) {
        return puedeCambiar().contains(estadoCita);
    }

    public static EstadoCita obtenerEstadoCitaPorCodigo(Long codigo) {

        ValoresNumericosUtils.validarLongPositivo(codigo, "El código es requerido y debe ser positivo");

        for (EstadoCita e: values()) {
            if (Objects.equals(e.codigo, codigo)) {
                return e;
            }
        }

        throw new DatoInvalidoException("Código de cita no válido: " + codigo);
    }
}
