/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deleteAccount;

import com.google.zxing.WriterException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import simpleForgotPassword.TwoFactorAuth;
import static simpleForgotPassword.TwoFactorAuth.getTOTPCode;
import simpleposts.PopUpMessage;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;

/**
 *
 * @author ethan
 */
public class DAController {
    @FXML 
    ImageView imgQR;
    
    @FXML
    TextField txtOtpCode;
    
    @FXML
    TextField txtNewPassword;
    
    @FXML
    Button btnChgPass;
    
    @FXML
    Button btnEnter;
    
    @FXML
    Label  lblSecretKey;
    
    RunSimpleUsers rsu;
    PopUpMessage pum;
    String secretKey;
    int userId;
    TwoFactorAuth tfa;
    DbDAO dao;
    FileReader fr;
    BufferedReader br;
    
    public void initialize() throws WriterException, IOException, SQLException{
        tfa = new TwoFactorAuth();
        dao = new DbDAO();
        fr = new FileReader("user.txt");
        br = new BufferedReader(fr);
        userId = Integer.parseInt(br.readLine());
        String email2 = dao.getUserEmailById(userId);
    }
    
    public void checkCode() throws SQLException{
        pum = new PopUpMessage();
        String code = txtOtpCode.getText();
        secretKey = dao.getUserKey(userId);
                if (code.equals(getTOTPCode(secretKey))) {
                    System.out.println("Logged in successfully");
                    pum.showInformationDialog("Correct OTP Code", "Correct OTP Code", "Correct OTP Code");
                    txtOtpCode.setVisible(false);
                    btnEnter.setVisible(false);
                } else {
                    System.out.println("Invalid 2FA Code, try again.");
                    pum.showInformationDialog("Invalid 2FA Code", "Invalid 2FA Code", "Invalid 2FA Code");
                }
    }
    
    @FXML
    void deleteAcc() throws FileNotFoundException, IOException, SQLException{
        dao = new DbDAO();
        fr = new FileReader("user.txt");
        br = new BufferedReader(fr);
        pum = new PopUpMessage();
        int userId = Integer.parseInt(br.readLine());
        
        if(btnEnter.isVisible()==false){
           pum.showConfirmationDialog("Account Deletion", "Confirm Deletion", "Do you want to delete your account?");
           dao.deleteUser(userId);
            dao.deleteUser(userId);
            switchSceneBackToLogin(); 
        }
        else{
            pum.showErrorDialog("Incomplete Verification", "Incomplete OTP Code Entry", "Please enter a otp code from your authenticator app.");
        }
        
        
        
    }
    
    @FXML
    public void switchSceneBackToLogin() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleusers/login.fxml");
    }
    
    @FXML
    public void switchSceneBackToHome() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }
   
    @FXML
    public void switchSceneBackToHomeD() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePageDark.fxml");
    }
}
 
