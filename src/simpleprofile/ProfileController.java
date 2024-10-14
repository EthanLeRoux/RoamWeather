/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleprofile;

import com.google.zxing.WriterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simpleForgotPassword.TwoFactorAuth;
import static simpleForgotPassword.TwoFactorAuth.createQRCode;
import static simpleForgotPassword.TwoFactorAuth.getGoogleAuthenticatorBarCode;
import simpleposts.PopUpMessage;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;

/**
 *
 * @author ethan
 */
public class ProfileController {
        
    @FXML 
    ImageView imgQR;
    
    @FXML
    TextField txtOtpCode;
    
    @FXML
    Label  lblSecretKey;
    
    @FXML
    Button btnForgotPass;
    
    @FXML
    Label  lblUsername;
    
    PopUpMessage pum;
    String secretKey;
    TwoFactorAuth tfa;
    DbDAO dao;
    FileReader fr;
    BufferedReader br;
    
    public void initialize() throws WriterException, IOException, SQLException{
        tfa = new TwoFactorAuth();
        dao = new DbDAO();
        fr = new FileReader("user.txt");
        br = new BufferedReader(fr);
        int userId = Integer.parseInt(br.readLine());
        secretKey = dao.getUserKey(userId);
        
        lblSecretKey.setText(secretKey);
            // Display barcode URL for Google Authenticator
            String email = "test@gmail.com";
            String companyName = "Roam Weather Test Trial";
            String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, email, companyName);

            // Generate QR code
            createQRCode(barCodeUrl, "QRCode.png", 300, 300);

            File file = new File("QRCode.png");
            Image qrImage = new Image(file.toURI().toString());
            imgQR.setImage(qrImage);
        
    }
    
    @FXML
    void forgotPass() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleForgotPassword/ForgotPassword.fxml");
    }
    
    @FXML
    void switchSceneHome() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }
}
