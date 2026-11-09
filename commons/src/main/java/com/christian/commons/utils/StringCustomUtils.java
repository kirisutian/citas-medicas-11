package com.christian.commons.utils;

import com.christian.commons.exceptions.DatoInvalidoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringCustomUtils {

    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void validarNoVacio(String texto, String mensaje) {
        if (texto == null || texto.isBlank())
            throw new DatoInvalidoException(mensaje);
    }

    public static void validarTamanio(String texto, Integer min, Integer max, String mensaje) {

        validarNoVacio(texto, mensaje);

        if (texto.length() < min || texto.length() > max)
            throw new DatoInvalidoException(mensaje);
    }

    public static String quitarAcentos(String texto) {
        return texto.toLowerCase()
                .replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o")
                .replace("ú", "u").replace("ü", "u");
    }

    public static String localDateAString(LocalDate fecha) {
        return fecha == null ? null : fecha.format(formato);
    }
}
