
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleposts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;
import space.dynomake.libretranslate.Language;

/**
 *
 * @author ethan
 */
public class PostController {
    @FXML
    private ListView<String> listPosts;
    FileReader fr;
    BufferedReader br;
    PostDAO pdao;
    DbDAO udao;
    String[] arrPostInfo;
    List<Post> posts;
    List<String> postStrings;
    PopUpMessage pum;
    
    @FXML
    TextArea txtPost;
    
    ArrayList<String> arrPost = new ArrayList<String>();
    Post newPost1 = new Post(1,"This is a post. Yeah.");
    Post newPost2 = new Post(2,"This is also a post. Haha!");
    Post newPost3 = new Post(1,"This is my reply to your post. Hahaha!");
    
    @FXML
Button btnPost;

@FXML
Button btnShowPosts;

@FXML
Label lblPosts;

@FXML
Button btnUpdatePost;

@FXML
Button btnDeletePost;

@FXML
ImageView btnHome;

@FXML
    MenuButton menuBtn;
    
    @FXML
    HBox hboxMenu;
    
    public void initialize() throws SQLException{
        showPosts();
    }
        
public void showPosts() throws SQLException {
        pdao = new PostDAO();
        udao = new DbDAO();
        
        posts = pdao.getAllPosts();
        listPosts.getItems().clear(); // Clear existing items
        
        for (Post post : posts) {
            //listPosts.getItems().add(post.toStringForListView());
            String postUsername = udao.getUserName(post.getPostUserId());
            post.setPostUserName(postUsername);
            
            if(post.getUpdatedAt()!=null){
                listPosts.getItems().add(post.toStringWithUserName() + "\n" +"Edited At: "+post.getUpdatedAt());
            }
            
            listPosts.getItems().add(post.toStringWithUserName() + "\n" +post.getCreatedAt());
        }
    }
    
    
public void addPost() throws FileNotFoundException, IOException, SQLException{
            fr = new FileReader("user.txt");
            br = new BufferedReader(fr);
            pdao = new PostDAO();
            udao = new DbDAO();
            pum = new PopUpMessage();
            
            int userId = Integer.parseInt(br.readLine());
            String postContent = txtPost.getText();

            // Add the post to the database
            pdao.addPost(userId, postContent);
            String username = udao.getUserName(userId);
            // Create a new Post object
            Post newPost = new Post(userId,username, postContent);
            //newPost.setPostUserName(udao.getUserName(userId));
            
            newPost.setPostUserName(username);
            // Add the new post to the ListView
            //listPosts.getItems().add(newPost.toStringForListView());
            listPosts.getItems().add(newPost.toStringWithUserName());
            showPosts();
            txtPost.setText("");     
    }

    public String extractBeforeNewline(String input) {
        if (input == null) {
            return null; // Handle null input
        }
        
        int newlineIndex = input.indexOf('\n');
        if (newlineIndex == -1) {
            return input; // No newline found, return the entire string
        }
        
        return input.substring(0, newlineIndex);
    }
    
    public String extractAfterNewline(String input) {
        if (input == null) {
                return null; // Handle null input
            }

            // Find the index of the first newline
            int firstNewlineIndex = input.indexOf('\n');
            if (firstNewlineIndex == -1) {
                return ""; // No newline found, return an empty string
            }

            // Find the index of the second newline
            int secondNewlineIndex = input.indexOf('\n', firstNewlineIndex + 1);
            if (secondNewlineIndex == -1) {
                return ""; // Only one newline found, return an empty string
            }

            // Extract the text between the first and second newline
            return input.substring(firstNewlineIndex + 1, secondNewlineIndex);
    }

public int getPostIdFromUsername(String username, String content) throws SQLException, FileNotFoundException, IOException{
    pdao = new PostDAO();
    udao = new DbDAO();
    fr = new FileReader("user.txt");
    br = new BufferedReader(fr);
    int userId = Integer.parseInt(br.readLine());
    int postId = pdao.getPostIdByUsernameAndContent(username, content);
    
    return postId;
}

public void updatePost() throws SQLException, IOException{
    pdao = new PostDAO();
    pum = new PopUpMessage();
    fr = new FileReader("user.txt");
    br = new BufferedReader(fr);
    udao = new DbDAO();
    
    // Get the selected post string from the ListView
    String selectedPost = listPosts.getSelectionModel().getSelectedItem();
    String newPostContent = txtPost.getText();
    
    String username = extractBeforeNewline(selectedPost);
    //extractAfterFirstBeforeSecondNewline
    //String username = extractAfterFirstBeforeSecondNewline(selectedPost);        
    String content = extractAfterNewline(selectedPost);
    int pId = pdao.getPostIdByUsernameAndContent(username, content);

    
    //int pId = extractPostIdFromString(selectedPost);
    
 int userId = Integer.parseInt(br.readLine());
//    String username = udao.getUserName(userId);
//    
//    int pId = getPostIdFromUsername(username);
    String[] post = pdao.getPostById(pId);
    
    int postUserId = Integer.parseInt(post[1]);
        if(postUserId == userId){
            boolean result = pum.showConfirmationDialog("Confirmation", "Confirmation Post Update", "Do you want to update this post?");
            if (result) {
                pdao.updatePostContentById(pId, newPostContent);
                txtPost.setText("");
                showPosts();
                pum.showInformationDialog("Update Post Dialog", "Post Update", "Post has been updated.");
            }
        }
        else{
            pum.showInformationDialog("Update Post Dialog", "Post Update Error", "You cannot update this post.");
        }
}

public void deletePost() throws SQLException, FileNotFoundException, IOException{
    pdao = new PostDAO();
    pum = new PopUpMessage();
    fr = new FileReader("user.txt");
    br = new BufferedReader(fr);
    
    // Get the selected post string from the ListView
    String selectedPost = listPosts.getSelectionModel().getSelectedItem();
    
    String username = extractBeforeNewline(selectedPost);
    String content = extractAfterNewline(selectedPost);
    int pId = pdao.getPostIdByUsernameAndContent(username, content);
    
    int userId = Integer.parseInt(br.readLine());
    String[] post = pdao.getPostById(pId);
    int postUserId = Integer.parseInt(post[1]);
    
     if(postUserId == userId){
         boolean result = pum.showConfirmationDialog("Confirmation", "Confirm Deletion", "Do you want to delete this post?");
            if (result) {
                pdao.deletePostById(pId);
                System.out.println("Record/Post Deleted Successfully!");
                txtPost.setText("");
                showPosts();
                pum.showInformationDialog("Delete Post Dialog", "Post Deletion", "Post has been deleted.");
            }
     }
     else{
                pum.showInformationDialog("Delete Post Dialog", "Post Deletion", "You cannot delete this post.");
            }
}

public void switchSceneHome() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }

} 
