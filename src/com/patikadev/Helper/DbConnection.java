package com.patikadev.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection = null;


    public Connection connectionDb(){
        try {
            this.connection = DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  this.connection;
    }

    public static Connection getInstance(){
        DbConnection dbConnection = new DbConnection();
        return  dbConnection.connectionDb();
    }
}
