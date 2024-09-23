/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleposts;

import java.sql.Timestamp;

/**
 *
 * fx-id
 * initialization
 * not beingrefernced by opbject
 * 
 * @author ethan
 */

public class Post {
    private int postUserId;
    private int postId;
    private String postContent;
    private String createdAt;
    private String updatedAt;
            
    
    public Post(int postUserId, String postContent) {
        this.postUserId = postUserId;
        this.postContent = postContent;
    }

    public Post(int postId, int postUserId,String postContent, String createdAt, String updatedAt) {
        this.postUserId = postUserId;
        this.postId = postId;
        this.postContent = postContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public int getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(int postUserId) {
        this.postUserId = postUserId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    @Override
    public String toString() {
        return "Post{" + "postUserId=" + postUserId + ", postContent=" + postContent +'}';
    }

   public String postConstructToString() {
        return "Post{" + "postUserId=" + postUserId + ", postId=" + postId + ", postContent=" + postContent + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
   public String toStringForListView() {
        return "POST ID: " + postId + "\n" +
                "USER ID: " + postUserId + "\n" +
               "POST CONTENT: " + postContent + "\n" +
               "CREATED AT: " + createdAt + "\n" +
               "UPDATED AT: " + updatedAt;
    }
}
