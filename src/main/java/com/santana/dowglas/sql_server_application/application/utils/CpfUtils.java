package com.santana.dowglas.sql_server_application.application.utils;

public class CpfUtils {

    public static String cleanCpf(String cpfNumber) {
        if (cpfNumber == null) return null;
        return cpfNumber.replaceAll("\\D", "");
    }
}