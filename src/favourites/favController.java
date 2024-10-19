/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package favourites;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import simpleposts.PopUpMessage;
import simpleusers.RunSimpleUsers;
import weathergen.WeatherGen;
import weatherlib.WeatherFace;

/**
 *
 * @author Bruneez
 */
public class favController {

    @FXML
    TextField searchTxt;

    @FXML
    Button readBtn;

    @FXML
    Button addBtn;

    @FXML
    Button deleteBtn;

    @FXML
    ListView<String> City_List;

    @FXML
    Button btnSearch;
    
    CrudOperator dao = new CrudOperator();
    
    @FXML
    String searchCity(){
        String city = searchTxt.getText();
        try {
            WeatherFace.Root weatherRoot = WeatherGen.fetchWeatherData(city, "metric");
            return city;
        } catch (Exception ex) {
            PopUpMessage pum = new PopUpMessage();
            pum.showErrorDialog("City name error", "City Does Not Exist Error", "City entered does not exist. Please enter a valid city name.");
        }
        return null;
    }
    
     public void initialize() throws SQLException{
        read();
        
        if(City_List.getItems().isEmpty()){
            PopUpMessage pum = new PopUpMessage();
            pum.showInformationDialog("Empty Favourites List", "Add to favorites", "Your favourites list is empty. Search for a city and add it to your list!");
        }
    }
     
    public void add() throws FileNotFoundException,IOException,SQLException {
      FileReader  fr = new FileReader("user.txt");
       BufferedReader br = new BufferedReader(fr);
        int userId = Integer.parseInt(br.readLine());
        //searchTxt = new TextField();
        //City_List = new ListView<String>();
        String search = searchCity();
        String City = "";
        if(search != null){
            City = searchTxt.getText();
        }
        
        
        if (City == null || City.isEmpty()) {
        System.out.println("City name is empty or null.");//
        return;
    }
        System.out.println("User ID: " + userId);
    System.out.println("City Name: " + City);
    
        Favourites favs = new Favourites(City,userId);
        //favs.setCity_name(City);
        dao.insertFavourite(favs);
        
        City_List.getItems().add(favs.toString());
        read();
        searchTxt.clear();
      br.close();
   
    }
    public void read(){
     List<Favourites> favs = dao.read(); 
     City_List.getItems().clear();
     
     for(Favourites faves: favs){
         City_List.getItems().add(faves.toString());
     }
     
    }
    
    public void update() throws SQLException{
        String selectedFave = City_List.getSelectionModel().getSelectedItem();
        int favID = extractFaveIdFromString(selectedFave);
        String City = searchTxt.getText();
        
        dao.updateFave(favID, City);
        System.out.println("Update success!");
        searchTxt.clear();
        read();
    }
    
    public void delete() throws SQLException{
       String selectedFav = City_List.getSelectionModel().getSelectedItem();
       int favID = extractFaveIdFromString(selectedFav);
        
       dao.removeFavourite(favID);
        System.out.println("DEETE SUCCESS!");
        searchTxt.clear();
        read();
    }
    
//    public int extractFaveIdFromString(String faveString) {
//    
//    String faveIdPrefix = "favId";
//    int startIndex = faveString.indexOf(faveIdPrefix);
//    
//    if (startIndex == -1) {
//        throw new IllegalArgumentException("Post string does not contain 'Favorite ID: '");
//    }
//
//    // Find the end index of the fave ID
//    int endIndex = faveString.indexOf('\n', startIndex);
//    if (endIndex == -1) {
//        endIndex = faveString.length(); // In case there's no newline at the end
//    }
//
//    // Extract the fave ID substring
//    String faveIdString = faveString.substring(startIndex + faveIdPrefix.length(), endIndex).trim();
//
//    // Parse the fave ID to integer
//    try {
//        return Integer.parseInt(faveIdString);
//    } catch (NumberFormatException e) {
//        throw new IllegalArgumentException("Post ID is not a valid integer", e);
//    }
//}
    
    public int extractFaveIdFromString(String favString) {
    // Find the start index of "favId="
    String favIdPrefix = "FAV";
    int startIndex = favString.indexOf(favIdPrefix);

    if (startIndex == -1) {
        throw new IllegalArgumentException("String does not contain 'favId='");
    }

    // Find the end index of the favId
    int endIndex = favString.indexOf(' ', startIndex);
    if (endIndex == -1) {
        endIndex = favString.length(); // In case there's no closing brace
    }

    // Extract the favId substring
    String favIdString = favString.substring(startIndex + favIdPrefix.length(), endIndex).trim();

    // Parse the favId to integer
    try {
        return Integer.parseInt(favIdString);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("favId is not a valid integer", e);
    }
}
    
    @FXML
    public void switchSceneHome() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        //rs.switchScene("simepleposts/scnPosts.fxml");
        rs.switchScene("/home/HomePage.fxml");
    }
    @FXML
    public void switchSceneHomed() throws IOException{
        RunSimpleUsers rs = new RunSimpleUsers();
        //rs.switchScene("simepleposts/scnPosts.fxml");
        rs.switchScene("/home/HomePageDark.fxml");
    }

}
    
   


