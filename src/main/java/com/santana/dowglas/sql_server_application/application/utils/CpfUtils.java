package com.santana.dowglas.sql_server_application.application.utils;

public class CpfUtils {

    public static String limparCpf(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("\\D", "");
    }
}
