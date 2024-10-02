/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleForgotPassword;

import com.google.zxing.WriterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static simpleForgotPassword.TwoFactorAuth.createQRCode;
import static simpleForgotPassword.TwoFactorAuth.getGoogleAuthenticatorBarCode;
import static simpleForgotPassword.TwoFactorAuth.getTOTPCode;
import simpleposts.PopUpMessage;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;

/**
 *
 * @author ethan
 */
public class OTPController {
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
        String email2 = dao.getUserEmailById(userId);
        
        secretKey = dao.getUserKey(userId);
        
        lblSecretKey.setText(secretKey);
            // Display barcode URL for Google Authenticator
            String email = "test@gmail.com";
            String companyName = "Roam Weather Test Trial for " + email2;
            String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, email, companyName);
            //System.out.println("Google Authenticator Barcode URL: " + barCodeUrl);

            // Generate QR code
            createQRCode(barCodeUrl, "QRCode.png", 300, 300);
            //System.out.println("QR Code generated at: QRCode.png");

            File file = new File("QRCode.png");
            Image qrImage = new Image(file.toURI().toString());
            imgQR.setImage(qrImage);
    }
    
    public void checkCode(){
        pum = new PopUpMessage();
        String code = txtOtpCode.getText();
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
    void changePassword() throws FileNotFoundException, IOException, SQLException{
        dao = new DbDAO();
        fr = new FileReader("user.txt");
        br = new BufferedReader(fr);
        pum = new PopUpMessage();
        int userId = Integer.parseInt(br.readLine());
        
        String newPass = txtNewPassword.getText();
        dao.updateUserPassword(userId, newPass);
        pum.showInformationDialog("Password Changed", "Password Changed", "Password Changed");
        switchSceneBackToLogin();
    }
    
    @FXML
    public void switchSceneBackToLogin() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleusers/login.fxml");
    }
}
