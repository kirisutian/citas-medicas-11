package com.christian.commons.docs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Problema", description = "Formato de error de la API (RFC 9457)")
public record ProblemaDoc(

        @Schema(example = "about:blank")
        String type,

        @Schema(example = "Not Found")
        String title,

        @Schema(description = "Código HTTP", example = "404")
        int status,

        @Schema(description = "Mensaje del error", example = "Producto no encontrado con id: 99")
        String detail,

        @Schema(description = "Ruta que produjo el error", example = "/api/productos/99")
        String instance,

        @Schema(description = "Solo aparece en errores de validación",
                example = "[\"nombre: El nombre es requerido\"]")
        List<String> errores
) {}