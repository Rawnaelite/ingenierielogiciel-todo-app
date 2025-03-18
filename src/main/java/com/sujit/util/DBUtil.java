package com.sujit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 *
 * @author Sujit
 */
public class DBUtil {

    public static Connection getConnection() throws SQLException {
        
        // forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://localhost/todo";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "root");

        return DriverManager.getConnection(url, props);

    }
}
