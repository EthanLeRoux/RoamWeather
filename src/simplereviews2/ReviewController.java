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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    public void addReview() throws IOException, SQLException, Exception {
        //comboItems();
        dao = new ReviewDAO();
        WeatherFace.Root weatherRoot;

        String textcity = txtCity.getText();
        weatherRoot = WeatherGen.fetchWeatherData(textcity, "metric");

        int userRating = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString());
        String reviewContent = txtReview.getText();
        int userId = getUserId();

    Review newReview = new Review(userId, textcity, reviewContent, userRating);
        dao.addReview(newReview, textcity);

        System.out.println("execcuted add!");
        getAll();
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

    public void deleteReview() throws SQLException {
        dao = new ReviewDAO();
        Review newReview = fromString(listReviews.getSelectionModel().getSelectedItem().toString());
        int revId = dao.getReviewId(newReview.getUserId(), newReview.getReviewContent());
        System.out.println(revId);
        //dao.deleteReview(selectedReviewId);
        dao.deleteReview(revId);
        getAll();
        System.out.println("Review deleted!");
        //refreshReviews(); // Assuming you have a method to refresh the ListView
    }

    public void showReviews() {

    }

    public void updateReview() throws SQLException {
        dao = new ReviewDAO();

        // Get the selected review from the ListView
        String selectedItem = listReviews.getSelectionModel().getSelectedItem().toString();

        // Check if an item is selected
        if (selectedItem != null && !selectedItem.isEmpty()) {
            // Parse the selected review string to a Review object
            Review newReview = fromString(selectedItem);

            // Retrieve the review ID using the Review object
            int revId = dao.getReviewId(newReview.getUserId(), newReview.getReviewContent());

            // Get updated values from the input fields
            String newContent = txtReview.getText();
            String newCity = txtCity.getText();
            int newRating = Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString());

            // Update the review
            dao.updateReview(revId, newContent, newCity, newRating);

            // Refresh the ListView
            getAll();

            System.out.println("Review updated!");
        } else {
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
