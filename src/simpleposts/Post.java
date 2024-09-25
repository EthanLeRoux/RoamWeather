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
 * not being refernced by opbject
 * 
 * @author ethan
 */

public class Post {
    private int postUserId;
    private int postId;
    private String postUserName;
    private String postContent;
    private String createdAt;
    private String updatedAt;

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }
            
    
    public Post(int postUserId, String postContent) {
        this.postUserId = postUserId;
        this.postContent = postContent;
    }

    public Post(int postUserId, String postUserName, String postContent) {
        this.postUserId = postUserId;
        this.postUserName = postUserName;
        this.postContent = postContent;
    }
    
    

    public Post(int postId, int postUserId,String postContent, String createdAt, String updatedAt) {
        this.postUserId = postUserId;
        this.postId = postId;
        this.postContent = postContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
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
   
   public String toStringWithUserName() {
        return postUserName +"\n" +
               postContent;
    }        
    
   public String toStringForListView() {
        return "POST ID: " + postId + "\n" +
                "USER ID: " + postUserId + "\n" +
               "POST CONTENT: " + postContent + "\n" +
               "CREATED AT: " + createdAt + "\n" +
               "UPDATED AT: " + updatedAt;
    }
}
