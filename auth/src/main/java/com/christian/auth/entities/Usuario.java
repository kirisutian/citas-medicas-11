package com.christian.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "USUARIOS_OAUTH")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 20, unique = true)
    private String username;

    /** Hash BCrypt (60 caracteres). Nunca se guarda ni se devuelve en texto plano. */
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    /**
     * EAGER: los roles se cargan junto con el usuario porque se necesitan
     * siempre al autenticar (para construir las autoridades y el claim "roles").
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USUARIOS_ROLES",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROL")
    )
    private Set<Rol> roles;
}