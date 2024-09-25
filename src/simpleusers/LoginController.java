/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleusers;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import simpletranslations.TranslateButtons;
import simpletranslations.TranslateGeneral;
import simpletranslations.TranslateLabels;
import space.dynomake.libretranslate.Language;

/**
 *
 * @author ethan
 */
public class LoginController {
    DbDAO dao;
    Stage stage;
    Scene scene;
    BufferedWriter bw;
    FileWriter fw;
    RunSimpleUsers rsu;
    
    @FXML
    TextField txtEmail;
    
    @FXML
    TextField txtLoginEmail;
    
    @FXML
    TextField txtLoginPassword;
    
    @FXML
    TextField txtUserName;
    
    @FXML
    TextField txtUserPassword;
    
    @FXML
    Label lblEmail;

    @FXML
    Label lblUsername;

    @FXML
    Label lblPassword;

    @FXML
    Label lblSwitchSceneLogin;

    @FXML
    Label lblAlreadyHaveAccount;
    
    @FXML
    Label lblNoAccountSignUp;
    
    @FXML
    Label lblLogin;
            
    @FXML
    Label lblSignUp;
    
    @FXML
    Button btnSignUp;
    
    @FXML
    Button btnLogin;
    
    @FXML
    MenuButton menuBtn;
    
    @FXML
    HBox hboxMenu;
            
    public void initialize() throws FileNotFoundException{
}
    
        @FXML
    public void switchSceneSignup() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("signup.fxml");
    }
    
    @FXML
    public void switchScenePosts() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("simepleposts/scnPosts.fxml");
    }
    
    public void loginUser() throws SQLException, IOException{
        String email = txtLoginEmail.getText();
        String password = txtLoginPassword.getText();
        User newUser = new User(email,password);
        dao = new DbDAO();
        String x = "/simpleSettings/Settings.fxml";
        User foundUser = dao.findUser(newUser);
        
        if(foundUser!= null){
            System.out.println("User Exists");
            
            this.saveUserSess(foundUser);
            rsu = new RunSimpleUsers();
            rsu.switchScene("/simpleposts/scnPosts.fxml");
        }
                
        else{
            System.out.println("User not Exist");
        }
    }
        
    
    public void saveUserSess(User user) throws IOException{
        fw = new FileWriter("user.txt");
        bw = new BufferedWriter(fw);
        String userId = Integer.toString(user.getUserId());
        bw.write(userId);
        System.out.println("Written to File!");
        bw.close();
    }
}
