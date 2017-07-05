package com.kehsa.phonecontainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created on 01.12.15.
 * @author kehsa
 */
public class PhoneSQLiteContainer extends AbstractPhoneSQLContainer {
    @Override
    void initDb() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:SQLiteDB");
            db = connection.createStatement();
            db.executeUpdate("CREATE TABLE IF NOT EXISTS brands"
                    + "(brand INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "brandname TEXT);");
            db.executeUpdate("CREATE TABLE IF NOT EXISTS phones"
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "brand INTEGER,"
                    + "model TEXT,"
                    + "cost REAL);");
            dbAddNewPhone = connection.prepareStatement("INSERT INTO phones"
                    + "(brand,model,cost) VALUES (?, ?, ?);");
            dbGetIdNewPhone = connection.prepareStatement("SELECT id FROM phones " +
                    "WHERE brand=? AND model=? AND cost=?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
