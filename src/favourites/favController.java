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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
    Label favLabel;

    @FXML
    private ListView<Favourites> City_List;

    CrudOperator dao = new CrudOperator();
    Favourites favs = new Favourites();

    @FXML
    public void initialize() {
        City_List.setCellFactory(param -> new FavouriteListCell());

        // Add a listener to filter input
        searchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            // Allow only letters and spaces
            if (!newValue.matches("[a-zA-Z ]*")) {
                searchTxt.setText(newValue.replaceAll("[^a-zA-Z ]", ""));
            }
        });
    }

    private class FavouriteListCell extends ListCell<Favourites> {

        private HBox content;
        private Text cityName;
        private Button viewWeatherBtn;
        private Button deleteBtn;

        public FavouriteListCell() {
            super();
            cityName = new Text();
            viewWeatherBtn = new Button("View weather");

            // Setting up delete button with an icon
            Image deleteImage = new Image(getClass().getResourceAsStream("/delete_icon.png"));
            ImageView deleteIcon = new ImageView(deleteImage);
            deleteIcon.setFitHeight(15);  // Set the height of the icon
            deleteIcon.setFitWidth(15);   // Set the width of the icon

            deleteBtn = new Button();
            deleteBtn.setGraphic(deleteIcon);  // Set the icon to the button

            content = new HBox(10, cityName, viewWeatherBtn, deleteBtn);
            content.setSpacing(15);

            // Add button actions
            viewWeatherBtn.setOnAction(e -> viewWeather());
            deleteBtn.setOnAction(e -> deleteFavourite());
        }

        @Override
        protected void updateItem(Favourites favourite, boolean empty) {
            super.updateItem(favourite, empty);
            if (favourite != null && !empty) {
                cityName.setText(favourite.getCity_name());
                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }//end class

        private void viewWeather() {
            Favourites fav = getItem();
            if (fav != null) {
                System.out.println("Viewing weather for: " + fav.getCity_name());
                // Implement weather fetching logic here
            }
        }

        private void deleteFavourite() {
            Favourites fav = getItem();
            if (fav != null) {
                try {
                    System.out.println("Deleting: " + fav.getCity_name());

                    // Call the delete function in your CRUD operator
                    dao.removeFavourite(favs.getFavId());

                    // Remove the item from the ListView
                    getListView().getItems().remove(fav);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

//    public void add() throws FileNotFoundException, IOException, SQLException {
//
//        FileReader fr = new FileReader("user.txt");
//        BufferedReader br = new BufferedReader(fr);
//        int userId = Integer.parseInt(br.readLine());
//        //searchTxt = new TextField();
//        //City_List = new ListView<String>();
//        String City = searchTxt.getText();
//        if (City == null || City.isEmpty()) {
//            System.out.println("City name is empty or null.");
//            return;
//        }
//        System.out.println("User ID: " + userId);
//        System.out.println("City Name: " + City);
//
//        Favourites favs = new Favourites(City, userId);
//        //favs.setCity_name(City);
//        dao.insertFavourite(favs);
//         
//        //convert object to a string and insert into the List
//        City_List.getItems().add(favs.toString());
//        read();
//         searchTxt.clear();
//        br.close();
//    }//end of the add method
//
//    public void read() {
//        List<Favourites> favs = dao.read();
//        //clear list and iterate over new items again
//        City_List.getItems().clear();
//        
//        for(Favourites faves: favs) {
//        City_List.getItems().add(faves.toString());
//    }
//    }
//    
//    public void update() {
//    try {
//        String selectedFave = City_List.getSelectionModel().getSelectedItem();
//        int favId = extractFaveIdFromString(selectedFave);
//        String city = searchTxt.getText();
//        
//        dao.updateFavourite(favId, city);
//        System.out.println("Update successful!");
//        searchTxt.clear();
//        read();
//        
//    } catch (IllegalArgumentException e) {
//        JOptionPane.showMessageDialog(null, "Input Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Unexpected Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }
//}//end update method
//    
//
//    public void delete() throws SQLException {
//        try{
//        String selectedFav = City_List.getSelectionModel().getSelectedItem();
//        int favID = extractFaveIdFromString(selectedFav);
//
//        dao.removeFavourite(favID);
//        System.out.println("DELETE SUCCESS!");
//        searchTxt.clear();
//        read();
//        } catch(SQLException e){
//              JOptionPane.showMessageDialog(null, "Unexpected Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Unexpected Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    }
//    }//end delete method
//    
//
//    public int extractFaveIdFromString(String favString) {
//        // Find the start index of "favId="
//        String favIdPrefix = "FavId= ";
//        int startIndex = favString.indexOf(favIdPrefix);
//
//        if (startIndex == -1) {
//            throw new IllegalArgumentException("String does not contain 'favId='");
//        }
//        //Find the end index of favId
//        int endIndex = favString.indexOf(" User_id= ", startIndex);
//        if (endIndex == -1) {
//            endIndex = favString.length(); // In case there's no closing brace
//        }
//
//        // Extract the favId substring
//        String faveIdString = favString.substring(startIndex + favIdPrefix.length(), endIndex).trim();
//
//        // Parse the fave ID to integer
//        try {
//            return Integer.parseInt(faveIdString);
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Post ID is not a valid integer", e);
//        }
//   } //end extract method

