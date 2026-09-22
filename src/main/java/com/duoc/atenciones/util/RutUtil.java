package com.duoc.atenciones.util;

public final class RutUtil {

    private RutUtil() {
    }

    public static String normalizar(String rut) {
        return rut == null ? "" : rut.replace(".", "").trim();
    }

    public static boolean tieneFormatoValido(String rut) {
        return normalizar(rut).matches("\\d{7,8}-[\\dkK]");
    }
}
