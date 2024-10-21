package simplereviews2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import simpleposts.PopUpMessage;
import simpleusers.DbDAO;
import simpleusers.RunSimpleUsers;
import weathergen.WeatherGen;
import weatherlib.WeatherFace;
import weatherlib.WeatherFace.Root;

public class ReviewController {

    @FXML
    ComboBox comboBox;

    @FXML
    TextArea txtReview;

    @FXML
    TextField txtCity;

    @FXML
    Button btnAddReview;

    @FXML
    Button btnDeleteReview;

    @FXML
    ListView listReviews;

    @FXML

    List<Review> allReviews;

    ObservableList<Integer> oal;

    ReviewDAO dao;

        PopUpMessage pum;

    Root fc;
    String city;

    @FXML
    public void initialize() throws SQLException {
        comboItems();
        getAll();
    }

    @FXML
    public void switchScene() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }
    
    @FXML
    public void switchScened() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePageDark.fxml");
    }

    public void addReview() throws IOException, SQLException, Exception {
    pum = new PopUpMessage();
    dao = new ReviewDAO();
    WeatherFace.Root weatherRoot;

    String textcity = txtCity.getText();

    try {
        // Fetch weather data for the entered city
        weatherRoot = WeatherGen.fetchWeatherData(textcity, "metric");

        int userRating = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString());
        String reviewContent = txtReview.getText();
        int userId = getUserId();

        // Create and add the review
        Review newReview = new Review(userId, textcity, reviewContent, userRating);
        dao.addReview(newReview, textcity);

        System.out.println("Executed add!");
        getAll();

    } catch (IOException e) {
        // Show a pop-up if the city isn't found or there is a network issue
        pum.showErrorDialog("City Not Found", "Invalid City", "The city '" + textcity + "' could not be found or there was a network issue.");
    } catch (Exception e) {
        // Handle other exceptions
        pum.showErrorDialog("City Not Found", "Invalid City", "The city '" + textcity + "' could not be found .");
//        throw e; // Re-throw to let higher-level methods handle it
    }
}

    
    private void showErrorDialog(String title, String header, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
}
private void showErrorPopup(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
}
    public void getAll() throws SQLException {

        dao = new ReviewDAO();
        allReviews = dao.getAllReviews();
        listReviews.getItems().clear(); // Clear existing items
        for (Review review : allReviews) {
            listReviews.getItems().add(review);
        }
    }

    public void comboItems() {
        oal = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        comboBox.setItems(oal);
    }

    public int getUserId() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("user.txt");
        BufferedReader br = new BufferedReader(fr);

        int userId = Integer.parseInt(br.readLine());
        return userId;
    }

    public void deleteReview() throws SQLException, IOException {
        dao = new ReviewDAO();
        pum = new PopUpMessage();
//        Review newReview = fromString(listReviews.getSelectionModel().getSelectedItem().toString());
        String str = listReviews.getSelectionModel().getSelectedItem().toString();
//        System.out.println(str);
        Review rev = this.parseReviewString(str);
        int revId = dao.getReviewId(rev.getUserId(), rev.getReviewContent());
        System.out.println(revId);
//        System.out.println(newReview.toString());
        pum.showConfirmationDialog("Review Deletion", "Deletion Confirmation", "Are you sure you want to delete this review?");
        dao.deleteReview(revId);
//        dao.deleteReview(revId);
        getAll();
//        System.out.println("Review deleted!");
        //refreshReviews(); // Assuming you have a method to refresh the ListView
    }

    public void showReviews() {

    }
    
    public Review parseReviewString(String reviewString) throws IOException {
    // Split the string by lines
    String[] lines = reviewString.split("\n");

    // Extract cityName from the first line (after "City:")
    String cityName = lines[0].substring(lines[0].indexOf("City:") + 6).trim();

    // Extract reviewContent from the second line
    String reviewContent = lines[1].trim();

    // Extract reviewRating from the third line (after "Rating:")
    int reviewRating = Integer.parseInt(lines[2].substring(lines[2].indexOf("Rating:") + 8).trim());

    int userid = this.getUserId();
    // Create and return a new Review object
    return new Review(userid, cityName, reviewContent, reviewRating);
}

    public void updateReview() throws SQLException, IOException {
         dao = new ReviewDAO();
        pum = new PopUpMessage();
//        Review newReview = fromString(listReviews.getSelectionModel().getSelectedItem().toString());
        String selectedItem = listReviews.getSelectionModel().getSelectedItem().toString();
//        System.out.println(str);
        
        

        // Check if an item is selected
        if (selectedItem != null && !selectedItem.isEmpty()) {
            pum.showConfirmationDialog("Review Update", "Update Confirmation", "Are you sure you want to edit this review?");
            Review rev = this.parseReviewString(selectedItem);
            int revId = dao.getReviewId(rev.getUserId(), rev.getReviewContent());

            // Get updated values from the input fields
            String newContent = txtReview.getText();
            String newCity = txtCity.getText();
            int newRating = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString());

            // Update the review
            dao.updateReview(revId, newContent, newCity, newRating);

            // Refresh the ListView
            getAll();

            pum.showInformationDialog("Successful Update", "Update Successful", "Review updated.");
            System.out.println("Review updated!");
        } else {
            pum.showErrorDialog("Select Review", "Error Selection", "No review selected.");
            System.out.println("No review selected!");
            // Optionally, show an alert to the user
        }
    }

    public static Review fromString(String reviewString) {
        // Extract the string parts between curly braces
        String[] parts = reviewString.replace("Review{", "").replace("}", "").split(", ");

        // Initialize variables to hold extracted data
        int userId = 0;
        String reviewContent = "";
        int reviewRating = 0;

        // Parse each part
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                switch (key) {
                    case "userId":
                        userId = Integer.parseInt(value);
                        break;
                    case "reviewContent":
                        reviewContent = value;
                        break;
                    case "reviewRating":
                        reviewRating = Integer.parseInt(value);
                        break;
                }
            }
        }

        // Create and return a new Review object with parsed values
        return new Review(userId, reviewContent, reviewRating);
    }
}
