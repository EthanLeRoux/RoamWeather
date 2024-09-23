/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleusers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ethan
 */

public class SideMenuController {
    public void initialize() throws FileNotFoundException{
        FileInputStream input = new FileInputStream("resources/menu_icon.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        
        //@FXML
        //MenuButton menuBtn;
        
        //menuBtn.setGraphic(imageView);
}
}
