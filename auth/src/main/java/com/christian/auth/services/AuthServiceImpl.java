package com.christian.auth.services;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.christian.auth.dto.LoginRequest;
import com.christian.auth.dto.TokenResponse;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetails userDetailsService;

    /** El mismo bean BCrypt que define SecurityConfig. */
    private final PasswordEncoder passwordEncoder;

    private final RSAKey rsaKey;

    /** Quién emite el token. Viene de application.properties. */
    @Value("${auth.jwt.issuer}")
    private String issuer;

    /** Duración del token en segundos (3600 si la propiedad no existe). */
    @Value("${auth.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    /**
     * Al arrancar, extraemos del JWKSource (el bean que define SecurityConfig)
     * la clave RSA con la que se firmarán los tokens. Si no hay clave, la
     * aplicación no debe arrancar: sin firma no hay autenticación posible.
     */
    public AuthServiceImpl(CustomUserDetails userDetailsService,
                           PasswordEncoder passwordEncoder,
                           JWKSource<SecurityContext> jwkSource) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        try {
            JWKSelector jwkSelector = new JWKSelector(
                    new JWKMatcher.Builder().keyType(KeyType.RSA).build()
            );
            var jwks = jwkSource.get(jwkSelector, null);
            if (jwks == null || jwks.isEmpty())
                throw new IllegalStateException("No se encontró ninguna clave RSA");
            rsaKey = (RSAKey) jwks.get(0);
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo obtener la clave RSA", e);
        }
    }

    @Override
    public TokenResponse autenticar(LoginRequest request) throws Exception {

        log.info("Cargando usuario {}", request.username());

        // Usuario inexistente y password incorrecto devuelven el MISMO error,
        // así no se revela qué usernames existen.
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(request.username());
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Compara el password en claro contra el hash guardado
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        Instant now = Instant.now();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)                                          // quién emite el token
                .subject(userDetails.getUsername())                      // a quién pertenece
                .issueTime(Date.from(now))                               // iat
                .expirationTime(Date.from(now.plusSeconds(expirationSeconds))) // exp
                .jwtID(UUID.randomUUID().toString())                     // jti: id único
                .claim("roles", userDetails.getAuthorities()             // claim propio con los roles
                        .stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        // El header incluye el keyID (kid) para que quien valide sepa qué clave pública usar
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
                claimsSet
        );

        signedJWT.sign(new RSASSASigner(rsaKey.toPrivateKey()));

        return new TokenResponse(signedJWT.serialize());
    }
}
