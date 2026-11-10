package com.christian.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioRequest(

        @NotBlank(message = "El username es requerido")
        @Size(min = 4, max = 20)
        String username,

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 8, max = 20)
        String password,

        @NotNull(message = "Los roles son requeridos")
        @Size(min = 1, message = "Debe haber al menos 1 rol")
        Set<String> roles
) {}