package com.christian.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLES_OAUTH")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Long id;

    /** Nombre único del rol. Convención: siempre con prefijo ROLE_ (ej. ROLE_ADMIN). */
    @Column(name = "NOMBRE", nullable = false, length = 15, unique = true)
    private String nombre;
}
