/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weathercreate;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import weathertoday.Today.Root;

/**
 *
 * @author User
 */
public class Gen {

    private static final String API_KEY = "b5b5939911ec3002b5d157e6767a849e";

    public static Root fetchWeatherData(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, "UTF-8");
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + API_KEY + "&units=metric";

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), Root.class);
        } else {
            InputStream errorStream = conn.getErrorStream();
            if (errorStream != null) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println("Error: " + errorLine);
                }
                errorReader.close();
            }
            throw new Exception("GET request failed with response code: " + responseCode);
        }
    }
}
