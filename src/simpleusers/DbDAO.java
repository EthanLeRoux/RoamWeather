/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleusers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ethan
 */

public class DbDAO {
        private DBConnection conn;
    
    public void insertUser(User user) throws SQLException{
        conn = new DBConnection();
        String insert_post_stmt = "INSERT INTO users (user_email, user_name, user_password) VALUES ('"+ user.getUserEmail()+"', '" + user.getUserName()+"', '"+user.getUserPassword()+"')";
        Statement stmt = conn.getConn().createStatement();
        stmt.execute(insert_post_stmt);
    }
    
        public User findUser(User user) throws SQLException{
        conn = new DBConnection();
        String read_post_stmt = "SELECT * FROM USERS WHERE user_email = '"+user.getUserEmail()+"' AND user_password = '"+user.getUserPassword()+"'";
        Statement stmt = conn.getConn().createStatement();
        stmt.execute(read_post_stmt);
        ResultSet rs = stmt.executeQuery(read_post_stmt);
         if (rs.next()) {
        user.setUserEmail(rs.getString("user_email"));
        user.setUserPassword(rs.getString("user_password"));
        user.setUserId(rs.getInt(1));
        }
        else {
            user = null;
        }
        rs.close();
        return user;
    }
    
    public Integer getUserId(String email, String password) throws SQLException {
        Integer userId = null;
        String query = "SELECT user_id FROM USERS WHERE user_email = '" + email + "' AND user_password = '" + password + "'";
        conn = new DBConnection();
        Statement stmt = conn.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            userId = rs.getInt("user_id");
        }

        rs.close();
        stmt.close();
        
        return userId;
    }

public String getUserName(int userId) throws SQLException {
    String sql = "SELECT user_name FROM users WHERE user_id = " + userId;
    String username = null;
    conn = new DBConnection();
    Statement stmt = conn.getConn().createStatement();
    ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                username = rs.getString("user_name");
            } else {
                System.out.println("No user found with ID: " + userId);
            }
            return username;
}   
    
    public void switchLogin() throws IOException{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scn = new Scene(root);
        primaryStage.setScene(scn);
    }
}
