/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleusers;

import favourites.PopUpMessages;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Bruneez
 */
public class SignUpController {
    DbDAO dao;
    Stage stage;
    Scene scene;
    BufferedWriter bw;
    FileWriter fw;
    RunSimpleUsers rsu;
    
    @FXML
    TextField txtUserName;

    @FXML
    private TextField txtUserPassword;
    
    
    @FXML
    private TextField conPassText;
    
    
    @FXML
    TextField txtEmail;


    @FXML
    Label lblSwitchSceneLogin;

    @FXML
    Label lblAlreadyHaveAccount;
    
    @FXML
    Label lblNoAccountSignUp;
    
    @FXML
    Label lblSignUp;
    
    @FXML
    Button btnSignUp;
    
    @FXML
    ImageView img;
    
    @FXML
    Label favLabel;      
  
      PopUpMessages popUps = new PopUpMessages();
      
    public void initialize(){
        // Load the GIF image from resources
        //Image image = new Image("file:/C:/Users/Bruneez/OneDrive/M%20y%20website/OneDrive/Documents/NetBeansProjects/Weather/weather.jpg");

        // Set the GIF image to the ImageView
        //img.setImage(image);
        
        
       // TranslateGeneral tg = new TranslateGeneral();

        // Translate Labels and Buttons to Dutch
//        lblEmail.setText(tg.translate(Language.DUTCH, lblEmail.getText()));
//        lblUsername.setText(tg.translate(Language.DUTCH, lblUsername.getText()));
//        lblPassword.setText(tg.translate(Language.DUTCH, lblPassword.getText()));
//        lblSignUp.setText(tg.translate(Language.DUTCH, lblSignUp.getText()));
//        lblAlreadyHaveAccount.setText(tg.translate(Language.DUTCH, lblAlreadyHaveAccount.getText()));
//        btnSignUp.setText(tg.translate(Language.DUTCH, btnSignUp.getText()));

  }
    
    public void createUser() throws SQLException, IOException {
        dao = new DbDAO();

        String email = txtEmail.getText();
        String userName = txtUserName.getText();
        String password = txtUserPassword.getText();
        String confirmpass = conPassText.getText();
        
        //validate the email 
        if (email.contains("@") && email.indexOf(".") < email.indexOf("@")) {
            System.out.println("Invalid email.Please enter a valid email address.");
              popUps.showInformationDialog("Invalid Email", "Email Error", "Invalid email. Please enter a valid email address.");
            return;
        }
        
         // Check if the username already exists in the database
        if (dao.doesUserNameExist(userName)) {
        System.out.println("Username already exists. Please use a different username.");
       popUps.showInformationDialog("Username Taken", "Username Error", "Username already exists. Please use a different username.");

        return;
        }
        
        // Check if the password and confirm password match
        if (!password.equals(confirmpass)) {
        popUps.showInformationDialog("Password Mismatch", "Password Error", "Passwords do not match. Please confirm your password.");

        return;
        }
    
        User users = new User(email, userName, password);
        dao.insertUser(users);
        System.out.println("User added Successfully");
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleusers/login.fxml");
    }
    
    @FXML
    public void switchSceneLogin() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/favourites/Favourite.fxml");
    }
    
}
