package com.christian.commons.clients;

import com.christian.commons.exceptions.ConflictoException;
import com.christian.commons.exceptions.DatoInvalidoException;
import com.christian.commons.exceptions.RecursoNoEncontradoException;
import com.christian.commons.exceptions.ServicioNoDisponibleException;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private static final String DETALLE_POR_DEFECTO = "Error en servicio remoto";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        String detalle = extraerDetalle(response);
        log.warn("Error en {}: status={}, detalle={}", methodKey, response.status(), detalle);

        return switch (response.status()) {
            case 400 -> new DatoInvalidoException(detalle);
            case 404 -> new RecursoNoEncontradoException(detalle);
            case 409 -> new ConflictoException(detalle);
            case 502, 503, 504 -> new ServicioNoDisponibleException(
                    "El servicio remoto no está disponible.");
            default -> defaultDecoder.decode(methodKey, response);
        };
    }

    private String extraerDetalle(Response response) {

        if (response.body() == null)
            return DETALLE_POR_DEFECTO;

        try(InputStream body = response.body().asInputStream()) {
            JsonNode json = MAPPER.readTree(body);
            return json.path("detail").asText(DETALLE_POR_DEFECTO);
        } catch (Exception e) {
            return DETALLE_POR_DEFECTO;
        }
    }
}
