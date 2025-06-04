package org.cefet.config;

public class DataBaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/luckiest_gamble?allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1212";

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }
}
