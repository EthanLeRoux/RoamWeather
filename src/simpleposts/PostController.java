
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
import simpletranslations.TranslateButtons;
import simpletranslations.TranslateGeneral;
import simpletranslations.TranslateLabels;
import simpleusers.DbDAO;
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
    MenuButton menuBtn;
    
    @FXML
    HBox hboxMenu;
    
    public void initialize() throws SQLException{
        showPosts();
        TranslateGeneral tg = new TranslateGeneral();
//Image image = new Image(getClass().getResource("/resources/menu_icon.png").toExternalForm());
        //ImageView imageView = new ImageView(image);
        //menuBtn.setGraphic(imageView);
        //hboxMenu.setSpacing(50);
// Translate Buttons and Labels to Japanese
//btnPost.setText(tg.translate(Language.JAPANESE, btnPost.getText()));
//btnShowPosts.setText(tg.translate(Language.JAPANESE, btnShowPosts.getText()));
//lblPosts.setText(tg.translate(Language.JAPANESE, lblPosts.getText()));
//btnUpdatePost.setText(tg.translate(Language.JAPANESE, btnUpdatePost.getText()));
//btnDeletePost.setText(tg.translate(Language.JAPANESE, btnDeletePost.getText()));


    }
        
public void showPosts() throws SQLException {
        pdao = new PostDAO();
        posts = pdao.getAllPosts();
        listPosts.getItems().clear(); // Clear existing items
        for (Post post : posts) {
            listPosts.getItems().add(post.toStringForListView());
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

            // Create a new Post object
            Post newPost = new Post(userId, postContent);

            // Add the new post to the ListView
            listPosts.getItems().add(newPost.toStringForListView());
            showPosts();
            txtPost.setText("");
            pum.showInformationDialog("Create Post Dialog", "Post Creation", "Post has been created.");
            
    }
    
public int extractPostIdFromString(String postString) {
        // Find the start index of "POST ID: "
        String postIdPrefix = "POST ID: ";
        int startIndex = postString.indexOf(postIdPrefix);

        if (startIndex == -1) {
            throw new IllegalArgumentException("Post string does not contain 'POST ID: '");
        }

        // Find the end index of the post ID
        int endIndex = postString.indexOf('\n', startIndex);
        if (endIndex == -1) {
            endIndex = postString.length(); // In case there's no newline at the end
        }

        // Extract the post ID substring
        String postIdString = postString.substring(startIndex + postIdPrefix.length(), endIndex).trim();

        // Parse the post ID to integer
        try {
            return Integer.parseInt(postIdString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Post ID is not a valid integer", e);
        }
}

public void updatePost() throws SQLException, IOException{
    pdao = new PostDAO();
    pum = new PopUpMessage();
    fr = new FileReader("user.txt");
    br = new BufferedReader(fr);
            
    // Get the selected post string from the ListView
    String selectedPost = listPosts.getSelectionModel().getSelectedItem();
    String newPostContent = txtPost.getText();
    int pId = extractPostIdFromString(selectedPost);

    int userId = Integer.parseInt(br.readLine());
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
    int pId = extractPostIdFromString(selectedPost);
    
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


} 
