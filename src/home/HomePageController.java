/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package home;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import simpleposts.PopUpMessage;
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
    MenuButton mbLang;

    @FXML
    MenuItem miSpanish;

    @FXML
    MenuItem miJapanese;

    @FXML
    MenuItem miChinese;

    @FXML
    MenuItem miKorean;

    @FXML
    MenuItem miFrench;

    @FXML
    MenuItem miDutch;

    @FXML
    Button btnMetric,
            btnImperial,
            btnStandard;
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
    Label lblToday;

    @FXML
    Label lbl5DayForecast, lblCityReview;

    @FXML
    Label lblMaximum;

    @FXML
    Label lblMinimum;

    @FXML
    Label lblFeelsLike;

    @FXML
    Label lblHumidity;

    @FXML
    Label lblSunset;

    @FXML
    Label lblSunrise;

    @FXML
    Button btnAddReview, btnSearch;

    @FXML
    ImageView icon,
            iconDay1,
            iconDay2,
            iconDay3,
            iconDay4,
            iconDay5,
            iProfile,
            iSettings,
            iFav,
            iForum,
            iEvents,
            iNews;
    String feelsLikeText = "Feels Like: ";
            PopUpMessage pum;


    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {

        try {
            icons();

            getAll();
            start();
            forecast();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnMetric.setOnMousePressed(e -> {
            btnMetric.setStyle("-fx-background-color: #f737f1; -fx-text-fill: white;");
        });

        btnMetric.setOnMouseReleased(e -> {
            btnMetric.setStyle("-fx-background-color: #2b93f3; -fx-text-fill: white;");
        });

        btnImperial.setOnMousePressed(e -> {
            btnImperial.setStyle("-fx-background-color: #f737f1; -fx-text-fill: white;");
        });

        btnImperial.setOnMouseReleased(e -> {
            btnImperial.setStyle("-fx-background-color: #2b93f3; -fx-text-fill: white;");
        });

        btnStandard.setOnMousePressed(e -> {
            btnStandard.setStyle("-fx-background-color: #f737f1; -fx-text-fill: white;");
        });

        btnStandard.setOnMouseReleased(e -> {
            btnStandard.setStyle("-fx-background-color: #2b93f3; -fx-text-fill: white;");
        });
    }

    // Instance variables for city and units
    // Example default city, you can modify it based on user input
    private String units = "metric"; // Default unit is metric (Celsius)
// Fetch weather data using the imperial units (Fahrenheit)

    public void imperial() throws Exception {
        // Get city from input field
        String city = txtCity.getText();
        writeCity(city);
        // Fetch weather data for the city in imperial units
        WeatherFace.Root weatherRoot = WeatherGen.fetchWeatherData(city, "imperial");

        // Set city name on the label
        lblCity.setText(weatherRoot.city.name);

        // Update the weather details (temperature, description, etc.) with imperial units
        String temps = String.format("%.2f", weatherRoot.list.get(0).getMain().getTemp()) + getUnitSymbol("imperial");
        String feelsLikes = String.format("%.2f", weatherRoot.list.get(0).getMain().getFeelsLike()) + getUnitSymbol("imperial");
        String tempMin = String.format("%.2f", weatherRoot.list.get(0).getMain().getTempMin()) + getUnitSymbol("imperial");
        String tempMax = String.format("%.2f", weatherRoot.list.get(0).getMain().getTempMax()) + getUnitSymbol("imperial");
        lblTemp.setText(temps);
        lblFl.setText(feelsLikes);
        lblMax.setText(tempMax);
        lblMin.setText(tempMin);
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};

        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date part (yyyy-mm-dd)
            String dateText = listItem.getDtTxt().split(" ")[0];

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + getUnitSymbol("imperial");
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + getUnitSymbol("imperial");
//            String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + getUnitSymbol("imperial");
//            String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + getUnitSymbol("imperial");
            String description = listItem.getWeather().get(0).getDescription();

            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + feelsLikeText + feelsLike + "\n"
                    //                    + "Min: " + tempMin + "\n"
                    //                    + "Max: " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
        }
        //lblDesc.setText(weatherRoot.list.get(0).getWeather().get(0).getDescription());
    }

// Fetch weather data using the metric units (Celsius)
    public void metric() throws Exception {
        // Get city from input field
        String city = txtCity.getText();
        writeCity(city);
        // Fetch weather data for the city in metric units
        WeatherFace.Root weatherRoot = WeatherGen.fetchWeatherData(city, "metric");

        // Set city name on the label
        lblCity.setText(weatherRoot.city.name);

        String temps = String.format("%.2f", weatherRoot.list.get(0).getMain().getTemp()) + getUnitSymbol("metric");
        String feelsLikes = String.format("%.2f", weatherRoot.list.get(0).getMain().getFeelsLike()) + getUnitSymbol("metric");
        String tempMin = String.format("%.2f", weatherRoot.list.get(0).getMain().getTempMin()) + getUnitSymbol("metric");
        String tempMax = String.format("%.2f", weatherRoot.list.get(0).getMain().getTempMax()) + getUnitSymbol("metric");
        lblTemp.setText(temps);
        lblFl.setText(feelsLikes);
        lblMax.setText(tempMax);
        lblMin.setText(tempMin);
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};

        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date part (yyyy-mm-dd)
            String dateText = listItem.getDtTxt().split(" ")[0];

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + getUnitSymbol("metric");
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + getUnitSymbol("metric");
//            String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + getUnitSymbol("metric");
//            String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + getUnitSymbol("metric");
            String description = listItem.getWeather().get(0).getDescription();
            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + feelsLikeText + feelsLike + "\n"
                    //                    + "Min: " + tempMin + "\n"
                    //                    + "Max: " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
        }
    }

// Fetch weather data using the standard units (Kelvin)
    public void standard() throws Exception {
        // Get city from input field
        String city = txtCity.getText();
        writeCity(city);
        // Fetch weather data for the city in standard units
        WeatherFace.Root weatherRoot = WeatherGen.fetchWeatherData(city, "standard");

        // Set city name on the label
        lblCity.setText(weatherRoot.city.name);
        String temps = String.format("%.2f", weatherRoot.list.get(0).getMain().getTemp()) + getUnitSymbol("standard");
        String feelsLikes = String.format("%.2f", weatherRoot.list.get(0).getMain().getFeelsLike()) + getUnitSymbol("standard");
        String tempMin = String.format("%.2f", weatherRoot.list.get(0).getMain().getTempMin()) + getUnitSymbol("standard");
        String tempMax = String.format("%.2f", weatherRoot.list.get(0).getMain().getTempMax()) + getUnitSymbol("standard");
        lblTemp.setText(temps);
        lblFl.setText(feelsLikes);
        lblMax.setText(tempMax);
        lblMin.setText(tempMin);
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};

        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date part (yyyy-mm-dd)
            String dateText = listItem.getDtTxt().split(" ")[0];

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + getUnitSymbol("standard");
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + getUnitSymbol("standard");
//            String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + getUnitSymbol("standard");
//            String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + getUnitSymbol("standard");
            String description = listItem.getWeather().get(0).getDescription();

            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + feelsLikeText + feelsLike + "\n"
                    //                    + "Min: " + tempMin + "\n"
                    //                    + "Max: " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
        }
    }
// Method to fetch weather data with metric units (Celsius)

    @FXML
    public void switchSceneDarkHome() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePageDark.fxml");
    }

    public void switchSceneLightHome() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }

    @FXML
    public void switchSceneRev() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simplereviews2/reviews.fxml");
    }

    @FXML
    public void switchSceneProfile() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleprofile/profile.fxml");
    }

    @FXML
    public void switchSceneFav() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/favourites/Favourites.fxml");
    }

    @FXML
    public void switchSceneSet() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleSettings/Settings.fxml");
    }

    @FXML
    public void switchSceneNews() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simplenewswithmaps/articles.fxml");
    }

    @FXML
    public void switchSceneForum() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/simpleposts/scnPosts.fxml");
    }

    public void getAll() throws SQLException {

        dao = new ReviewDAO();
        allReviews = dao.getAllReviews();
        listReviews.getItems().clear(); // Clear existing items
        for (Review review : allReviews) {
            listReviews.getItems().add(review);
        }
    }

    public void getCityRev(String cityName) throws SQLException {
        dao = new ReviewDAO();
        allReviews = dao.getReviewsByCity(cityName.toUpperCase()); // Fetch reviews for the specific city
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

    //@FXML
    public void icons() {
//C:\Appdev\SimpleUsers\src\resources
        Image profile = new Image("/resources/profile.png");
        Image settings = new Image("/resources/settings.png");
        Image fav = new Image("/resources/favorites.png");
        Image forum = new Image("/resources/forum.png");
        Image events = new Image("/resources/events.png");
        Image news = new Image("/resources/news.png");

        iProfile.setImage(profile);
        iSettings.setImage(settings);
        iFav.setImage(fav);
        iForum.setImage(forum);
        iEvents.setImage(events);
        iNews.setImage(news);

    }

    public void today() throws Exception {
        // Get the city name from the text field
        String place = txtCity.getText();
        Image sun = new Image("/resources/sun.png");
        Image fewClouds = new Image("/resources/cloudy.png");
//        Image scatteredClouds = new Image("file:C:/Appdev/WeatherFinal/src/home/cloud.png");
//        Image brokenClouds = new Image("file:C:/Appdev/WeatherFinal/src/home/cloud.png");
        Image showerRain = new Image("/resources/heavy-rain.png");
        Image rain = new Image("/resources/rain-drops.png");
        Image thunderStorm = new Image("/resources/sun.png");
        Image snow = new Image("/resources/sun.png");
        Image mist = new Image("/resources/smoke.png");
        // Fetch weather data for the given city
        Today.Root todayRoot;
        try{
        pum = new PopUpMessage();

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
}
catch(Exception e){

        pum.showErrorDialog("City Not Found", "Invalid City", "The city '" + place + "' could not be found .");
    
}
        getCityRev(place);
        forecast();
    }

    public void start() throws Exception {
        // Get the city name from the text field
        String place = "Cape Town";
        String selectedUnits = units;
        Image fewClouds = new Image("/resources/cloudy.png");
        Image sun = new Image("/resources/sun.png");
        Image showerRain = new Image("/resources/heavy-rain.png");
        Image rain = new Image("/resources/rain-drops.png");
        Image thunderStorm = new Image("/resources/sun.png");
        Image snow = new Image("/resources/sun.png");
        Image mist = new Image("/resources/smoke.png");
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
            switch (w.description) {
                case "thunderstorm with light rain":
                case "thunderstorm with rain":
                case "thunderstorm with heavy rain":
                case "light thunderstorm":
                case "thunderstorm":
                case "heavy thunderstorm":
                case "ragged thunderstorm":
                case "thunderstorm with light drizzle":
                case "thunderstorm with drizzle":
                case "thunderstorm with heavy drizzle":
                    icon.setImage(thunderStorm);
                    break;

                case "light intensity drizzle":
                case "drizzle":
                case "heavy intensity drizzle":
                case "light intensity drizzle rain":
                case "drizzle rain":
                case "heavy intensity drizzle rain":
                case "shower rain and drizzle":
                case "heavy shower rain and drizzle":
                case "shower drizzle":
                    icon.setImage(showerRain);
                    break;

                case "light rain":
                case "moderate rain":
                case "heavy intensity rain":
                case "very heavy rain":
                case "extreme rain":
                case "freezing rain":
                case "light intensity shower rain":
                case "shower rain":
                case "heavy intensity shower rain":
                case "ragged shower rain":
                    icon.setImage(rain);
                    break;

                case "light snow":
                case "snow":
                case "heavy snow":
                case "sleet":
                case "light shower sleet":
                case "shower sleet":
                case "light rain and snow":
                case "rain and snow":
                case "light shower snow":
                case "shower snow":
                case "heavy shower snow":
                    icon.setImage(snow);
                    break;

                case "mist":
                case "smoke":
                case "haze":
                case "sand/dust whirls":
                case "fog":
                case "sand":
                case "dust":
                case "volcanic ash":
                case "squalls":
                case "tornado":
                    icon.setImage(mist);
                    break;

                case "clear sky":
                    icon.setImage(sun);
                    break;

                case "few clouds":
                case "scattered clouds":
                case "broken clouds":
                case "overcast clouds":
                    icon.setImage(fewClouds);
                    break;

                default:
                    // Optionally, handle cases where no matching description is found
                    lblDesc.setText("Weather data unavailable");
                    break;
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
        WeatherFace.Root weatherRoot;
        weatherRoot = WeatherGen.fetchWeatherData(place, units);  // Fetch weather data for the city

        // Set city name
        lblCity.setText(weatherRoot.city.name);

        // Define an array to hold the five forecast labels
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};
        ImageView[] forecastIcons = {iconDay1, iconDay2, iconDay3, iconDay4, iconDay5};
        // Loop through the weather list (3-hour intervals) and extract the forecast for 5 days
        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date part (yyyy-mm-dd)
            String dateText = listItem.getDtTxt().split(" ")[0];

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + getUnitSymbol(selectedUnits);
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + getUnitSymbol(selectedUnits);
//        String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + getUnitSymbol(selectedUnits);
//        String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + getUnitSymbol(selectedUnits);
            String description = listItem.getWeather().get(0).getDescription();

            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + feelsLikeText + feelsLike + "\n"
                    //                + "Min: " + tempMin + "\n"
                    //                + "Max: " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
            switch (description) {
                case "thunderstorm with light rain":
                case "thunderstorm with rain":
                case "thunderstorm with heavy rain":
                case "light thunderstorm":
                case "thunderstorm":
                case "heavy thunderstorm":
                case "ragged thunderstorm":
                case "thunderstorm with light drizzle":
                case "thunderstorm with drizzle":
                case "thunderstorm with heavy drizzle":
                    forecastIcons[i].setImage(thunderStorm);
                    break;

                case "light intensity drizzle":
                case "drizzle":
                case "heavy intensity drizzle":
                case "light intensity drizzle rain":
                case "drizzle rain":
                case "heavy intensity drizzle rain":
                case "shower rain and drizzle":
                case "heavy shower rain and drizzle":
                case "shower drizzle":
                    forecastIcons[i].setImage(showerRain);
                    break;

                case "light rain":
                case "moderate rain":
                case "heavy intensity rain":
                case "very heavy rain":
                case "extreme rain":
                case "freezing rain":
                case "light intensity shower rain":
                case "shower rain":
                case "heavy intensity shower rain":
                case "ragged shower rain":
                    forecastIcons[i].setImage(rain);
                    break;

                case "light snow":
                case "snow":
                case "heavy snow":
                case "sleet":
                case "light shower sleet":
                case "shower sleet":
                case "light rain and snow":
                case "rain and snow":
                case "light shower snow":
                case "shower snow":
                case "heavy shower snow":
                    forecastIcons[i].setImage(snow);
                    break;

                case "mist":
                case "smoke":
                case "haze":
                case "sand/dust whirls":
                case "fog":
                case "sand":
                case "dust":
                case "volcanic ash":
                case "squalls":
                case "tornado":
                    forecastIcons[i].setImage(mist);
                    break;

                case "clear sky":
                    forecastIcons[i].setImage(sun);
                    break;

                case "few clouds":
                case "scattered clouds":
                case "broken clouds":
                case "overcast clouds":
                    forecastIcons[i].setImage(fewClouds);
                    break;

                default:
                    forecastIcons[i].setImage(null);  // Optionally clear the icon if no match is found
                    break;
            }
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
        writeCity(city);
        String selectedUnits = units;  // Use the selected unit (metric, imperial, standard)
        Image fewClouds = new Image("/resources/cloudy.png");
        Image sun = new Image("/resources/sun.png");
        Image showerRain = new Image("/resources/heavy-rain.png");
        Image rain = new Image("/resources/rain-drops.png");
        Image thunderStorm = new Image("/resources/sun.png");
        Image snow = new Image("/resources/sun.png");
        Image mist = new Image("/resources/smoke.png");
        // Fetch weather data for the city with the selected units
        weatherRoot = WeatherGen.fetchWeatherData(city, selectedUnits);

        // Set city name on the label
        lblCity.setText(weatherRoot.city.name);

        // Define an array to hold the five forecast labels
        Label[] forecastLabels = {lblDay1, lblDay2, lblDay3, lblDay4, lblDay5};
        ImageView[] forecastIcons = {iconDay1, iconDay2, iconDay3, iconDay4, iconDay5};
        // Loop through the weather list (3-hour intervals) and extract the forecast for 5 days
        for (int i = 0; i < 5; i++) {  // Only the first 5 days
            WeatherFace.List listItem = weatherRoot.getList().get(i * 8); // Each 8th item represents a 24-hour interval

            // Extract the date part (yyyy-mm-dd)
            String dateText = listItem.getDtTxt().split(" ")[0];

            // Extract temperature and other weather conditions
            String temp = String.format("%.2f", listItem.getMain().getTemp()) + getUnitSymbol(selectedUnits);
            String feelsLike = String.format("%.2f", listItem.getMain().getFeelsLike()) + getUnitSymbol(selectedUnits);
//        String tempMin = String.format("%.2f", listItem.getMain().getTempMin()) + getUnitSymbol(selectedUnits);
//        String tempMax = String.format("%.2f", listItem.getMain().getTempMax()) + getUnitSymbol(selectedUnits);
            String description = listItem.getWeather().get(0).getDescription();

            // Build the string for the current day's forecast
            String forecastText = dateText + "\n"
                    + temp + "\n"
                    + feelsLikeText + feelsLike + "\n"
                    //                + "Min: " + tempMin + "\n"
                    //                + "Max: " + tempMax + "\n"
                    + description + "\n";

            // Set the forecast text for the corresponding label
            forecastLabels[i].setText(forecastText);
            switch (description) {
                case "thunderstorm with light rain":
                case "thunderstorm with rain":
                case "thunderstorm with heavy rain":
                case "light thunderstorm":
                case "thunderstorm":
                case "heavy thunderstorm":
                case "ragged thunderstorm":
                case "thunderstorm with light drizzle":
                case "thunderstorm with drizzle":
                case "thunderstorm with heavy drizzle":
                    forecastIcons[i].setImage(thunderStorm);
                    break;

                case "light intensity drizzle":
                case "drizzle":
                case "heavy intensity drizzle":
                case "light intensity drizzle rain":
                case "drizzle rain":
                case "heavy intensity drizzle rain":
                case "shower rain and drizzle":
                case "heavy shower rain and drizzle":
                case "shower drizzle":
                    forecastIcons[i].setImage(showerRain);
                    break;

                case "light rain":
                case "moderate rain":
                case "heavy intensity rain":
                case "very heavy rain":
                case "extreme rain":
                case "freezing rain":
                case "light intensity shower rain":
                case "shower rain":
                case "heavy intensity shower rain":
                case "ragged shower rain":
                    forecastIcons[i].setImage(rain);
                    break;

                case "light snow":
                case "snow":
                case "heavy snow":
                case "sleet":
                case "light shower sleet":
                case "shower sleet":
                case "light rain and snow":
                case "rain and snow":
                case "light shower snow":
                case "shower snow":
                case "heavy shower snow":
                    forecastIcons[i].setImage(snow);
                    break;

                case "mist":
                case "smoke":
                case "haze":
                case "sand/dust whirls":
                case "fog":
                case "sand":
                case "dust":
                case "volcanic ash":
                case "squalls":
                case "tornado":
                    forecastIcons[i].setImage(mist);
                    break;

                case "clear sky":
                    forecastIcons[i].setImage(sun);
                    break;

                case "few clouds":
                case "scattered clouds":
                case "broken clouds":
                case "overcast clouds":
                    forecastIcons[i].setImage(fewClouds);
                    break;

                default:
                    forecastIcons[i].setImage(null);  // Optionally clear the icon if no match is found
                    break;
            }
        }
    }

// Helper method to get the unit symbol based on the unit type
    private String getUnitSymbol(String unitType) {
        switch (unitType) {
            case "imperial":
                return "°F"; // Fahrenheit for imperial
            case "standard":
                return "K"; // Kelvin for standard
            default:
                return "°C"; // Celsius for metric and default
        }
    }

    private void writeCity(String city) throws IOException {
        FileWriter fw = new FileWriter("city.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(city);
        bw.close();
    }

}
