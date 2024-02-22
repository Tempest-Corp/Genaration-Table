package com.frame.naina.connection;

import java.sql.*;

import com.frame.naina.Data.Database;

public class Connect {

    public static Connection getConnection(Database database) {
        Connection con = null;
        try {
            // "jdbc:%s://%s:%s/%s?user=%s&password=%s&useSSL=%s&allowPublicKeyRetrieval=%s"
            Class.forName(database.getDriver());
            con = DriverManager.getConnection(
                    "jdbc:" + database.getName() + "://" + database.getHost() + ":" + database.getPort() + "/"
                            + database.getDatabaseName(),
                    database.getUsername(), database.getPassword());
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

}
