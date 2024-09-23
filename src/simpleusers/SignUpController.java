/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleusers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simpletranslations.TranslateGeneral;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;
import simpleusers.User;
import space.dynomake.libretranslate.Language;

/**
 *
 * @author ethan
 */
public class SignUpController {
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
    Label lblSignUp;
    
    @FXML
    Button btnSignUp;
    
    @FXML
    Button btnLogin;
            
    public void initialize(){
        TranslateGeneral tg = new TranslateGeneral();

        // Translate Labels and Buttons to Dutch
//        lblEmail.setText(tg.translate(Language.DUTCH, lblEmail.getText()));
//        lblUsername.setText(tg.translate(Language.DUTCH, lblUsername.getText()));
//        lblPassword.setText(tg.translate(Language.DUTCH, lblPassword.getText()));
//        lblSignUp.setText(tg.translate(Language.DUTCH, lblSignUp.getText()));
//        lblAlreadyHaveAccount.setText(tg.translate(Language.DUTCH, lblAlreadyHaveAccount.getText()));
//        btnSignUp.setText(tg.translate(Language.DUTCH, btnSignUp.getText()));

    }
    
    public void createUser() throws SQLException, IOException{
        dao = new DbDAO();
        
        String email = txtEmail.getText();
        String userName = txtUserName.getText();
        String password = txtUserPassword.getText();
        
        User u = new User(email,userName,password);
        dao.insertUser(u);
        System.out.println("User added Successfully");
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("login.fxml");
    }
    
    @FXML
    public void switchSceneLogin() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("login.fxml");
    }
}
