package com.christian.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioUpdateRequest(

        // Opcional: si no viene (null), se conserva el password actual.
        // Si viene, debe cumplir el mismo tamaño que al registrar.
        @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
        String password,

        @NotEmpty(message = "Los roles son requeridos")
        Set<String> roles
) {}