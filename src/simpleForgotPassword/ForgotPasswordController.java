/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleForgotPassword;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;

/**
 *
 * @author ethan
 */
public class ForgotPasswordController {
    @FXML
    TextField txtEmail;
    
    BufferedWriter bw;
    FileWriter fw;
    DbDAO dao;
    
    @FXML
    public void switchSceneOTP() throws IOException, SQLException{
        writeForgotEmail();
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleForgotPassword/OTP.fxml");
    }
    
    public void writeForgotEmail() throws IOException, SQLException{
        fw = new FileWriter("email.txt");
        bw = new BufferedWriter(fw);
        dao = new DbDAO();
        
        String email = txtEmail.getText();
        bw.write(email);
        System.out.println("Email Written to File!");
        
        int userid = dao.getUserId(email);
        fw = new FileWriter("user.txt");
        bw = new BufferedWriter(fw);
        String userId = Integer.toString(userid);
        bw.write(userId);
        System.out.println("user id Written to File!");
        
        bw.close();
    }
}
