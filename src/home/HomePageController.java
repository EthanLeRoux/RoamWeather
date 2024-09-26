/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package home;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simplereviews2.Review;
import simplereviews2.ReviewDAO;
import simpleusers.RunSimpleUsers;
import weathercreate.Gen;
import weathergen.WeatherGen;
import weatherlib.WeatherFace;
import weathertoday.Today;

/**
 * FXML Controller class
 *
 * @author User
 */
public class HomePageController implements Initializable {

    ReviewDAO dao;

    List<Review> allReviews;
    List<Review> cities;

    @FXML
    TextField txtCity;

    @FXML
    Label lblCity,
            lblDate,
            lblFl,
            lblRise,
            lblSet,
            lblHum,
            lblMin,
            lblMax,
            lblTemp,
            lblDay1,
            lblDay2,
            lblDay3,
            lblDay4,
            lblDay5,
            lblDesc;

    @FXML
    ListView listReviews;

    @FXML
    ListView cityHistory;

    @FXML
    ImageView icon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getAll();
            start();
            forecast();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void switchSceneRev() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simplereviews2/reviews.fxml");
    }

    public void getAll() throws SQLException {

        dao = new ReviewDAO();
        allReviews = dao.getAllReviews();
        listReviews.getItems().clear(); // Clear existing items
        for (Review review : allReviews) {
            listReviews.getItems().add(review);
        }
    }

//    public void getCity() throws SQLException {
//
//        dao = new ReviewDAO();
//        cities = dao.getCities();
//        cityHistory.getItems().clear(); // Clear existing items
//        for (Review review : cities) {
//            cityHistory.getItems().add(review);
//        }
//    }
    public void getCityRev(String cityName) throws SQLException {
        dao = new ReviewDAO();
        allReviews = dao.getReviewsByCity(cityName); // Fetch reviews for the specific city
        listReviews.getItems().clear(); // Clear existing items

        if (allReviews.isEmpty()) {
            // If no reviews for the city, display a message
            listReviews.getItems().add("Be The First To Review " + cityName.toUpperCase() + "!!!");
        } else {
            // Add only the reviews for the specific city
            for (Review review : allReviews) {
                listReviews.getItems().add(review);
            }
        }
    }

    public void today() throws Exception {
        // Get the city name from the text field
        String place = txtCity.getText();
        Image sun = new Image("file:C:/Appdev/WeatherFinal/src/home/sun.png");
        Image fewClouds = new Image("file:C:/Appdev/WeatherFinal/src/home/cloudy.png");
        Image scatteredClouds = new Image("file:C:/Appdev/WeatherFinal/src/home/cloud.png");
        Image brokenClouds = new Image("file:C:/Appdev/WeatherFinal/src/home/cloud.png");
        Image showerRain = new Image("file:C:/Appdev/WeatherFinal/src/home/heavy-rain.png");
        Image rain = new Image("file:C:/Appdev/WeatherFinal/src/home/rain-drops.png");
        Image thunderStorm = new Image("file:C:/Appdev/WeatherFinal/src/home/sun.png");
        Image snow = new Image("file:C:/Appdev/WeatherFinal/src/home/sun.png");
        Image mist = new Image("file:C:/Appdev/WeatherFinal/src/home/smoke.png");
        // Fetch weather data for the given city
        Today.Root todayRoot;

        todayRoot = Gen.fetchWeatherData(place);

        // Update the labels with fetched data
        lblCity.setText(todayRoot.name); // City name
        if (todayRoot.main != null) {
            lblTemp.setText(Double.toString(todayRoot.main.temp));
        } else {
            lblTemp.setText("Temperature data unavailable");
        }
        // Convert the timestamp 'dt' from UNIX to a readable date (optional improvement)
        lblDate.setText(convertUnixToDate(todayRoot.dt));
        // Fetch the first weather description from the weather list
        if (todayRoot.weather != null && !todayRoot.weather.isEmpty()) {
            Today.Weather w = todayRoot.weather.get(0);
            lblDesc.setText(w.description); // Weather description

            // Using .equals() for string comparison
            // Thunderstorm conditions
            if (w.description.equals("thunderstorm with light rain")
                    || w.description.equals("thunderstorm with rain")
                    || w.description.equals("thunderstorm with heavy rain")
                    || w.description.equals("light thunderstorm")
                    || w.description.equals("thunderstorm")
                    || w.description.equals("heavy thunderstorm")
                    || w.description.equals("ragged thunderstorm")
                    || w.description.equals("thunderstorm with light drizzle")
                    || w.description.equals("thunderstorm with drizzle")
                    || w.description.equals("thunderstorm with heavy drizzle")) {
                icon.setImage(thunderStorm);

                // Drizzle conditions
            } else if (w.description.equals("light intensity drizzle")
                    || w.description.equals("drizzle")
                    || w.description.equals("heavy intensity drizzle")
                    || w.description.equals("light intensity drizzle rain")
                    || w.description.equals("drizzle rain")
                    || w.description.equals("heavy intensity drizzle rain")
                    || w.description.equals("shower rain and drizzle")
                    || w.description.equals("heavy shower rain and drizzle")
                    || w.description.equals("shower drizzle")) {
                icon.setImage(showerRain);

                // Rain conditions
            } else if (w.description.equals("light rain")
                    || w.description.equals("moderate rain")
                    || w.description.equals("heavy intensity rain")
                    || w.description.equals("very heavy rain")
                    || w.description.equals("extreme rain")
                    || w.description.equals("freezing rain")
                    || w.description.equals("light intensity shower rain")
                    || w.description.equals("shower rain")
                    || w.description.equals("heavy intensity shower rain")
                    || w.description.equals("ragged shower rain")) {
                icon.setImage(rain);

                // Snow conditions
            } else if (w.description.equals("light snow")
                    || w.description.equals("snow")
                    || w.description.equals("heavy snow")
                    || w.description.equals("sleet")
                    || w.description.equals("light shower sleet")
                    || w.description.equals("shower sleet")
                    || w.description.equals("light rain and snow")
                    || w.description.equals("rain and snow")
                    || w.description.equals("light shower snow")
                    || w.description.equals("shower snow")
                    || w.description.equals("heavy shower snow")) {
                icon.setImage(snow);

                // Atmosphere conditions
            } else if (w.description.equals("mist")
                    || w.description.equals("smoke")
                    || w.description.equals("haze")
                    || w.description.equals("sand/dust whirls")
                    || w.description.equals("fog")
                    || w.description.equals("sand")
                    || w.description.equals("dust")
                    || w.description.equals("volcanic ash")
                    || w.description.equals("squalls")
                    || w.description.equals("tornado")) {
                icon.setImage(mist);

                // Clear sky condition
            } else if (w.description.equals("clear sky")) {
                icon.setImage(sun);

                // Cloud conditions
            } else if (w.description.equals("few clouds")
                    || w.description.equals("scattered clouds")
                    || w.description.equals("broken clouds")
                    || w.description.equals("overcast clouds")) {
                icon.setImage(fewClouds);
            }
        }

        // Set the sunrise and sunset times (convert UNIX timestamp to human-readable format)
        if (todayRoot.sys != null) {
            lblRise.setText(convertUnixToTime(todayRoot.sys.sunrise, todayRoot.timezone));
            lblSet.setText(convertUnixToTime(todayRoot.sys.sunset, todayRoot.timezone));
        }
        if (todayRoot.main != null) {
            if (lblFl != null) {
                lblFl.setText(Double.toString(todayRoot.main.feels_like));
            }
            if (lblHum != null) {
                lblHum.setText(Double.toString(todayRoot.main.humidity));
            }

            if (lblMax != null) {
                lblMax.setText(Double.toString(todayRoot.main.temp_max));
            }
            if (lblMin != null) {
                lblMin.setText(Double.toString(todayRoot.main.temp_min));
            }

        }

        getCityRev(place);
        forecast();
    }

    public void start() throws Exception {
        // Get the city name from the text field
        String place = "Cape Town";

        // Fetch weather data for the given city
        Today.Root todayRoot;

        todayRoot = Gen.fetchWeatherData(place);

        // Update the labels with fetched data
        lblCity.setText(todayRoot.name); // City name
        if (todayRoot.main != null) {
            lblTemp.setText(Double.toString(todayRoot.main.temp));
        } else {
            lblTemp.setText("Temperature data unavailable");
        }
        // Convert the timestamp 'dt' from UNIX to a readable date (optional improvement)
        lblDate.setText(convertUnixToDate(todayRoot.dt));
        // Fetch the first weather description from the weather list
        if (todayRoot.weather != null && !todayRoot.weather.isEmpty()) {
            Today.Weather w = todayRoot.weather.get(0);
            lblDesc.setText(w.description); // Weather description
        }

        // Set the sunrise and sunset times (convert UNIX timestamp to human-readable format)
        if (todayRoot.sys != null) {
            lblRise.setText(convertUnixToTime(todayRoot.sys.sunrise, todayRoot.timezone));
            lblSet.setText(convertUnixToTime(todayRoot.sys.sunset, todayRoot.timezone));
        }
        if (todayRoot.main != null) {
            if (lblFl != null) {
                lblFl.setText(Double.toString(todayRoot.main.feels_like));
            }
            if (lblHum != null) {
                lblHum.setText(Double.toString(todayRoot.main.humidity));
            }

            if (lblMax != null) {
                lblMax.setText(Double.toString(todayRoot.main.temp_max));
            }
            if (lblMin != null) {
                lblMin.setText(Double.toString(todayRoot.main.temp_min));
            }

        }
        WeatherFace.Root weatherRoot;
        weatherRoot = WeatherGen.fetchWeatherData(place);  // Fetch weather data for the city

        // Set city name
        lblCity.setText(weatherRoot.city.name);

        // Define an array to hold the five forecast labels
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};

        // Loop through the weather list (3-hour intervals) and extract the forecast for 5 days
        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date and time
            String dateText = listItem.getDtTxt().split(" ")[0]; // Only use the date part (yyyy-mm-dd)

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + "°C";
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + "°C ";
            String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + "°C ";
            String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + "°C ";
            String description = listItem.getWeather().get(0).getDescription();

            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + "Feels Like: " + feelsLike + "\n"
                    + "Min: " + tempMin + "\n"
                    + "Max : " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
        }
        getCityRev(place);
        forecast();

    }

// Helper method to convert UNIX timestamp to a readable date format
    private String convertUnixToDate(int unixSeconds) {
        // Convert seconds to milliseconds
        Date date = new java.util.Date((long) unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        // sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

// Helper method to convert UNIX timestamp to a readable time format
    private String convertUnixToTime(int unixSeconds, int timezoneOffsetSeconds) {
        // Adjust the UNIX time with the city's timezone offset (in seconds)
        long adjustedTimeMillis = (unixSeconds + timezoneOffsetSeconds) * 1000L;

        // Convert to Date
        Date time = new java.util.Date(adjustedTimeMillis);

        // Format the time as hours, minutes, and seconds
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));  // Use UTC as baseline because we've adjusted the time

        return sdf.format(time);
    }

    public void forecast() throws Exception {
        // Fetch weather data for the specified city
        WeatherFace.Root weatherRoot;
        String city = txtCity.getText();  // Get city from input field
        weatherRoot = WeatherGen.fetchWeatherData(city);  // Fetch weather data for the city

        // Set city name
        lblCity.setText(weatherRoot.city.name);

        // Define an array to hold the five forecast labels
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};

        // Loop through the weather list (3-hour intervals) and extract the forecast for 5 days
        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date and time
            String dateText = listItem.getDtTxt().split(" ")[0]; // Only use the date part (yyyy-mm-dd)

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + "°C";
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + "°C ";
            String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + "°C ";
            String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + "°C ";
            String description = listItem.getWeather().get(0).getDescription();

            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + "Feels Like: " + feelsLike + "\n"
                    + "Min: " + tempMin + "\n"
                    + "Max : " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
        }
    }

}
