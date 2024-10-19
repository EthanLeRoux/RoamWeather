/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simpleusers;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simpleposts.PostDAO;

/**
 *
 * @author ethan
 */

public class RunSimpleUsers extends Application{
    private static Stage primaryStage;
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RunSimpleUsers.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));//Change back to sign up after fixing posts page
        Scene scn = new Scene(root);
        primaryStage.setScene(scn);
        primaryStage.show();
    }
    
    public void switchScene(String fxml) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scn = new Scene(root);
        primaryStage.setScene(scn);
        primaryStage.show();
    }
    
}
