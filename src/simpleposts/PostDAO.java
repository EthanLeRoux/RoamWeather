/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleposts;

import com.sun.jdi.connect.spi.Connection;
import simpleusers.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.derby.iapi.sql.PreparedStatement;

/**
 *
 * @author ethan
 */

public class PostDAO {
        private DBConnection conn;
        Statement stmt;
        PreparedStatement pstmt;
        DbDAO udao;
    
    public void addPost(int userId, String postContent) throws SQLException {
        conn = new DBConnection();
        stmt = conn.getConn().createStatement();  

        postContent = postContent.replace("'", "''");
        String sql = String.format("INSERT INTO posts (user_id, post_content, post_created_at) VALUES (%d, '%s', CURRENT_TIMESTAMP)", userId, postContent);
        stmt.executeUpdate(sql);
    }
     
    public void updatePost(int postId, String postContent) throws SQLException {
        conn = new DBConnection();
        stmt = conn.getConn().createStatement();

        postContent = postContent.replace("'", "''");

        String sql = String.format("UPDATE posts SET post_content = '%s' WHERE post_id = %d", postContent, postId);
        stmt.executeUpdate(sql);
    }

        public String[] getPostById(int postId) throws SQLException {
            String sql = "SELECT post_id, user_id, post_content, post_created_at, post_updated_at FROM posts WHERE post_id = " + postId;
            conn = new DBConnection();
            stmt = conn.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String[] columnValues = new String[5];
            
            if (rs.next()) {
                int id = rs.getInt("post_id");
                int userId = rs.getInt("user_id");
                String postContent = rs.getString("post_content");
                java.sql.Timestamp createdAt = rs.getTimestamp("post_created_at");
                java.sql.Timestamp updatedAt = rs.getTimestamp("post_updated_at");

                // Store these values in variables or use them as needed
                System.out.println("Post ID: " + id);
                System.out.println("User ID: " + userId);
                System.out.println("Post Content: " + postContent);
                System.out.println("Created At: " + createdAt);
                System.out.println("Updated At: " + updatedAt);

                columnValues[0] = Integer.toString(rs.getInt("post_id"));
                columnValues[1] = Integer.toString(rs.getInt("user_id"));
                columnValues[2] = rs.getString("post_content");
                columnValues[3] = rs.getTimestamp("post_created_at").toString();
                columnValues[4] = rs.getTimestamp("post_updated_at") != null ? rs.getTimestamp("post_updated_at").toString() : "NULL";
    }
            return columnValues;

}
        
public int getPostIdByUsernameAndContent(String username, String postContent) throws SQLException {
    String sql = "SELECT p.post_id " +
                 "FROM posts p " +
                 "JOIN users u ON p.user_id = u.user_id " +
                 "WHERE u.user_name = '" + username + "' " +
                 "AND p.post_content = '" + postContent + "'";
    
    conn = new DBConnection();
    stmt = conn.getConn().createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    
    int postId = -1;  // Default value if no matching post is found
    if (rs.next()) {
        postId = rs.getInt("post_id");
    }

    rs.close();
    stmt.close();
    conn.getConn().close();

    return postId;  // Return the post ID or -1 if not found
}
 
        public void deletePostById(int postId) throws SQLException{
            conn = new DBConnection();
            stmt = conn.getConn().createStatement();
            
             // SQL query to delete a post by post_id
            String sql = String.format("DELETE FROM posts WHERE post_id = %d", postId);
    
            // Execute the delete query
            stmt.executeUpdate(sql);
        }
        
        public List<String> getPostsAsStringList(List<Post> posts) {
        List<String> postStrings = new ArrayList<>();
        for (Post post : posts) {
            postStrings.add(post.toStringForListView());
        }
        return postStrings;
    }
        
        public List<Post> getAllPosts() throws SQLException {
        String sql = "SELECT * FROM posts";
        Statement stmt = null;
        ResultSet rs = null;
        List<Post> posts = new ArrayList<>();
            conn = new DBConnection();
            stmt = conn.getConn().createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("post_id");
                int userId = rs.getInt("user_id");
                String postContent = rs.getString("post_content");
                java.sql.Timestamp createdAt = rs.getTimestamp("post_created_at");
                java.sql.Timestamp updatedAt = rs.getTimestamp("post_updated_at");
                
                String createdAtStr;
                if (createdAt != null) {
                    createdAtStr = createdAt.toString();
                } else {
                    createdAtStr = null;
                }

                String updatedAtStr;
                if (updatedAt != null) {
                    updatedAtStr = updatedAt.toString();
                } else {
                    updatedAtStr = null;
                }
                
                posts.add(new Post(id, userId, postContent, createdAtStr, updatedAtStr));
            }
            return posts;
        }
        
       public void updatePostContentById(int postId, String newPostContent) throws SQLException {
    // Initialize the DB connection and statement
    conn = new DBConnection();
    stmt = conn.getConn().createStatement();

    // Escape single quotes in the postContent to prevent SQL syntax errors and injection
    newPostContent = newPostContent.replace("'", "''");

    // SQL query to update post_content and UPDATED_AT
    String sql = String.format("UPDATE posts SET post_content = '%s', POST_UPDATED_AT = CURRENT_TIMESTAMP WHERE post_id = %d", newPostContent, postId);
    
    // Execute the update query
    stmt.executeUpdate(sql);
}

    }
       