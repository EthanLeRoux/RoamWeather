
package weathergen;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import weatherlib.WeatherFace.Root;

public class WeatherGen {

    private static final String API_KEY = "b5b5939911ec3002b5d157e6767a849e";  

   public static Root fetchWeatherData(String city, String units) throws Exception {
    // Encode city name to handle spaces or special characters
    String encodedCity = URLEncoder.encode(city, "UTF-8");

    // Construct the URL with both the city and units parameters
    String urlString = "https://api.openweathermap.org/data/2.5/forecast?q=" + encodedCity + "&appid=" + API_KEY + "&units=" + units;

    // Create URL object and open connection
    URL url = new URL(urlString);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    // Check the response code
    int responseCode = conn.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the JSON response to Root object using Gson
        Gson gson = new Gson();
        return gson.fromJson(response.toString(), Root.class);
    } else {
        // Handle error response
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
