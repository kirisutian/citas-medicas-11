package com.christian.auth.mapper;

import com.christian.auth.dto.UsuarioRequest;
import com.christian.auth.dto.UsuarioResponse;
import com.christian.auth.entities.Rol;
import com.christian.auth.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    /** Entidad -> DTO de salida. Nunca incluye el password. */
    public UsuarioResponse entityToResponse(Usuario usuario) {
        return usuario == null ? null
                : new UsuarioResponse(
                usuario.getUsername(),
                usuario.getRoles().stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toSet()));
    }

    /**
     * DTO de entrada -> entidad.
     * El password llega ya encriptado y los roles ya resueltos desde la BD:
     * el mapper no consulta ni encripta nada, solo ensambla.
     */
    public Usuario requestToEntity(UsuarioRequest request, String password, Set<Rol> roles) {
        if (request == null) return null;
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username().trim());
        usuario.setPassword(password.trim());
        usuario.setRoles(roles);
        return usuario;
    }
}
