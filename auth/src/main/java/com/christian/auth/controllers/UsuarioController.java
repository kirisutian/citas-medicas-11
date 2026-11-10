package com.christian.auth.controllers;

import com.christian.auth.dto.UsuarioRequest;
import com.christian.auth.dto.UsuarioResponse;
import com.christian.auth.dto.UsuarioUpdateRequest;
import com.christian.auth.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Set<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> registrar(
            @Valid @RequestBody UsuarioRequest request) {
        // 201 Created: el POST crea un recurso nuevo
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(request));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable String username,
                                                      @Valid @RequestBody UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(username, request));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> eliminar(@PathVariable String username) {
        usuarioService.eliminar(username);
        // 204 No Content: se eliminó y no hay nada que devolver
        return ResponseEntity.noContent().build();
    }
}
