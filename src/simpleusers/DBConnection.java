/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleusers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ethan
 */
public class DBConnection {
    private Connection dbconn = null;
        
        public Connection getConn()throws SQLException{
            String dbUrl = "jdbc:mysql://sql5.freemysqlhosting.net:3306/sql5739430";
            String dbUser ="sql5739430";
            String dbPass = "SpCbwhv7en";
            dbconn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            return dbconn;
        }
}
