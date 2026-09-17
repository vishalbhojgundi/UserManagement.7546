package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "DB-Connection";

    private static final String USERNAME = "DB-Name";

    private static final String PASSWORD = "DB-Passwordgit";

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
    }
}