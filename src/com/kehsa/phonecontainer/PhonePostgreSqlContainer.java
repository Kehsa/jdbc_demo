package com.kehsa.phonecontainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created on 01.12.15.
 * @author kehsa
 */
public class PhonePostgreSqlContainer extends AbstractPhoneSQLContainer {
    @Override
    void initDb() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql:phonedatabase",
                    "kehsa",
                    "myawesomepassword");
            db = connection.createStatement();
            dbAddNewPhone = connection.prepareStatement("INSERT INTO phones"
                    + "(brand,model,cost) VALUES (?, ?, ?);");
            dbGetIdNewPhone = connection.prepareStatement("SELECT id FROM phones " +
                    "WHERE brand=? AND model=? AND cost=?;");
            db.executeUpdate("CREATE TABLE IF NOT EXISTS brands"
                    + "(brand SERIAL PRIMARY KEY,"
                    + "brandname TEXT);");
            db.executeUpdate("CREATE TABLE IF NOT EXISTS phones"
                    + "(id SERIAL PRIMARY KEY,"
                    + "brand INTEGER,"
                    + "model TEXT,"
                    + "cost FLOAT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
