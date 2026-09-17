package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        String databaseUrl = System.getenv("MYSQL_PUBLIC_URL");

        if (databaseUrl == null || databaseUrl.isEmpty()) {
            throw new Exception("MYSQL_PUBLIC_URL is not configured.");
        }

        // Railway gives: mysql://...
        // JDBC needs: jdbc:mysql://...
        if (databaseUrl.startsWith("mysql://")) {
            databaseUrl = "jdbc:" + databaseUrl;
        }

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(databaseUrl);
    }
}