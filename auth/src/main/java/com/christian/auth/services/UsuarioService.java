package com.christian.auth.services;

import com.christian.auth.dto.UsuarioRequest;
import com.christian.auth.dto.UsuarioResponse;
import com.christian.auth.dto.UsuarioUpdateRequest;

import java.util.Set;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse actualizar(String username, UsuarioUpdateRequest request);

    void eliminar(String username);
}