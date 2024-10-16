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
import java.sql.PreparedStatement;
import simpleForgotPassword.TwoFactorAuth;
import simpleusers.DBConnection;
import simpleusers.User;
/**
 *
 * @author ethan
 */
public class DbDAO {
        private DBConnection conn;
        TwoFactorAuth tfa;
        
//    public void insertUser(User user) throws SQLException{
//        conn = new DBConnection();
//        tfa = new TwoFactorAuth();
//        String key = tfa.generateSecretKey();
//        String insert_post_stmt = "INSERT INTO users (user_email, user_name, user_password) VALUES ('"+ user.getUserEmail()+"', '" + user.getUserName()+"', '"+user.getUserPassword()+"')";
//        Statement stmt = conn.getConn().createStatement();
//        stmt.execute(insert_post_stmt);
//    }
        
        public void insertUser(User user) throws SQLException {
    conn = new DBConnection();
    tfa = new TwoFactorAuth();
    
    // Generate the secret key
    String key = tfa.generateSecretKey();
    
    // Prepare the SQL insert statement with the secret key included
    String insert_user_stmt = "INSERT INTO users (user_email, user_name, user_password, user_key) VALUES (?, ?, ?, ?)";
    
    // Use PreparedStatement to prevent SQL injection and safely insert data
    PreparedStatement pstmt = conn.getConn().prepareStatement(insert_user_stmt);
    pstmt.setString(1, user.getUserEmail());
    pstmt.setString(2, user.getUserName());
    pstmt.setString(3, user.getUserPassword());
    pstmt.setString(4, key); // Include the generated secret key
    
    // Execute the insert statement
    pstmt.executeUpdate();
}

    
    public void readPosts() throws SQLException{
        conn = new DBConnection();
        String read_post_stmt = "SELECT * FROM POSTS";
        
        Statement stmt = conn.getConn().createStatement();
        stmt.execute(read_post_stmt);
        
        ResultSet rs = stmt.executeQuery(read_post_stmt);
        int row = 0;
        while(rs.next()){
            row++;
            System.out.println("Record ["+row+"]"+" {Subject_Code: " +rs.getInt(1)+", Subject_Name: "+ rs.getString(2) + "}");
        }
        rs.close();
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
        // Assuming user has other fields to set from the database
        // user.setOtherField(rs.getString("other_field"));
        } 
        else {
            user = null;
        }
        rs.close();
        return user;
    }
        
        public User findUserWIthUserName(User user) throws SQLException{
        conn = new DBConnection();
        String read_post_stmt = "SELECT * FROM USERS WHERE user_name = '"+user.getUserEmail()+"' AND user_password = '"+user.getUserPassword()+"'";
        Statement stmt = conn.getConn().createStatement();
        stmt.execute(read_post_stmt);
        
        ResultSet rs = stmt.executeQuery(read_post_stmt);
         if (rs.next()) {
        user.setUserName(rs.getString("user_name"));
        user.setUserPassword(rs.getString("user_password"));
        user.setUserId(rs.getInt(1));
        // Assuming user has other fields to set from the database
        // user.setOtherField(rs.getString("other_field"));
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
        
        Statement stmt = conn.getConn().createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            userId = rs.getInt("user_id");
        }

        rs.close();
        stmt.close();
        
        return userId;
    }    
    
    public void switchLogin() throws IOException{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scn = new Scene(root);
        primaryStage.setScene(scn);
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
    
    public int getUserId(String email) throws SQLException {
    String sql = "SELECT user_id FROM users WHERE user_email = ?";
    int userId = -1;  // Initialize to -1 to indicate user not found
    conn = new DBConnection();
    PreparedStatement pstmt = conn.getConn().prepareStatement(sql);
    pstmt.setString(1, email);  // Set email in the prepared statement
    ResultSet rs = pstmt.executeQuery();

    if (rs.next()) {
        userId = rs.getInt("user_id");
    } else {
        System.out.println("No user found with email: " + email);
    }

    return userId;
}

    public String getUserKey(int userId) throws SQLException {
    String sql = "SELECT user_key FROM users WHERE user_id = " + userId;
    String userKey = null;
    conn = new DBConnection();
    Statement stmt = conn.getConn().createStatement();
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
        userKey = rs.getString("user_key");
    } else {
        System.out.println("No user found with ID: " + userId);
    }

    return userKey;
}

    
    public boolean doesUserNameExist(String userName) throws SQLException {
        conn = new DBConnection();
    String query = "SELECT COUNT(*) FROM USERS WHERE user_name = ?";
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        stmt = conn.getConn().prepareStatement(query);
        stmt.setString(1, userName);
        rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Return true if the count is greater than 0 (username exists)
        }
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return false;
}
    
public void updateUserPassword(int userId, String newPassword) throws SQLException {
    conn = new DBConnection();
    
    // SQL update statement to change the user's password based on user_id
    String update_password_stmt = "UPDATE users SET user_password = ? WHERE user_id = ?";
    
    // Use PreparedStatement to safely update the password
    PreparedStatement pstmt = conn.getConn().prepareStatement(update_password_stmt);
    pstmt.setString(1, newPassword);  // Set new password
    pstmt.setInt(2, userId);          // Set user_id to identify the user
    
    // Execute the update statement
    int rowsUpdated = pstmt.executeUpdate();
    
    if (rowsUpdated > 0) {
        System.out.println("Password updated successfully for user ID: " + userId);
    } else {
        System.out.println("No user found with the ID: " + userId);
    }
}

public String getUserEmailById(int userId) throws SQLException {
    conn = new DBConnection();
    String userEmail = null;  // Initialize variable to hold the email

    // SQL select statement to retrieve the user's email based on user_id
    String select_email_stmt = "SELECT user_email FROM users WHERE user_id = ?";
    
    // Use PreparedStatement to safely execute the query
    PreparedStatement pstmt = conn.getConn().prepareStatement(select_email_stmt);
    pstmt.setInt(1, userId);  // Set user_id to identify the user
    
    // Execute the query
    ResultSet rs = pstmt.executeQuery();
    
    // Check if a result was returned
    if (rs.next()) {
        userEmail = rs.getString("user_email");  // Retrieve the email from the result set
    } else {
        System.out.println("No user found with the ID: " + userId);
    }
    
    rs.close();  // Close the ResultSet
    pstmt.close();  // Close the PreparedStatement
    return userEmail;  // Return the user's email
}


}