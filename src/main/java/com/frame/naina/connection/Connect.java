package com.frame.naina.connection;

import java.sql.*;

public class Connect {

    public static Connection getConnection(String database, String username, String password) {
        Connection con = null;
        try {
            // "jdbc:%s://%s:%s/%s?user=%s&password=%s&useSSL=%s&allowPublicKeyRetrieval=%s"
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database, username, password);
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

}
