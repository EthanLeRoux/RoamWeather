
package weatherlib;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;



public class WeatherFace {
      public WeatherFace() {
    }

    public static class Root {

        public Root(String cod, int message, int cnt, ArrayList<List> list, City city) {
            this.cod = cod;
            this.message = message;
            this.cnt = cnt;
            this.list = list;
            this.city = city;
        }
        public String cod;
        public int message;
        public int cnt;
        public ArrayList<List> list;
        public City city;

        // Getters and setters
        public String getCod() { return cod; }
        public void setCod(String cod) { this.cod = cod; }

        public int getMessage() { return message; }
        public void setMessage(int message) { this.message = message; }

        public int getCnt() { return cnt; }
        public void setCnt(int cnt) { this.cnt = cnt; }

        public ArrayList<List> getList() { return list; }
        public void setList(ArrayList<List> list) { this.list = list; }

        public City getCity() { return city; }
        public void setCity(City city) { this.city = city; }
    }

    public static class City {
        public int id;
        public String name;
        public Coord coord;
        public String country;
        public int population;
        public int timezone;
        public int sunrise;
        public int sunset;

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Coord getCoord() { return coord; }
        public void setCoord(Coord coord) { this.coord = coord; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public int getPopulation() { return population; }
        public void setPopulation(int population) { this.population = population; }

        public int getTimezone() { return timezone; }
        public void setTimezone(int timezone) { this.timezone = timezone; }

        public int getSunrise() { return sunrise; }
        public void setSunrise(int sunrise) { this.sunrise = sunrise; }

        public int getSunset() { return sunset; }
        public void setSunset(int sunset) { this.sunset = sunset; }
    }

    public static class Clouds {
        public int all;

        // Getters and setters
        public int getAll() { return all; }
        public void setAll(int all) { this.all = all; }
    }

    public static class Coord {
        public double lat;
        public double lon;

        // Getters and setters
        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }

        public double getLon() { return lon; }
        public void setLon(double lon) { this.lon = lon; }
    }

    public static class List {
        public int dt;
        public Main main;
        public ArrayList<Weather> weather;
        public Clouds clouds;
        public Wind wind;
        public int visibility;
        public double pop;
        public Sys sys;
        public String dt_txt;
        public Rain rain;

        // Getters and setters
        public int getDt() { return dt; }
        public void setDt(int dt) { this.dt = dt; }

        public Main getMain() { return main; }
        public void setMain(Main main) { this.main = main; }

        public ArrayList<Weather> getWeather() { return weather; }
        public void setWeather(ArrayList<Weather> weather) { this.weather = weather; }

        public Clouds getClouds() { return clouds; }
        public void setClouds(Clouds clouds) { this.clouds = clouds; }

        public Wind getWind() { return wind; }
        public void setWind(Wind wind) { this.wind = wind; }

        public int getVisibility() { return visibility; }
        public void setVisibility(int visibility) { this.visibility = visibility; }

        public double getPop() { return pop; }
        public void setPop(double pop) { this.pop = pop; }

        public Sys getSys() { return sys; }
        public void setSys(Sys sys) { this.sys = sys; }

        public String getDtTxt() { return dt_txt; }
        public void setDtTxt(String dt_txt) { this.dt_txt = dt_txt; }

        public Rain getRain() { return rain; }
        public void setRain(Rain rain) { this.rain = rain; }

     }

    public static class Rain {
        @JsonProperty("3h")
        public double _3h;

        // Getters and setters
        public double get3h() { return _3h; }
        public void set3h(double _3h) { this._3h = _3h; }
    }

    public static class Sys {
        public String pod;

        // Getters and setters
        public String getPod() { return pod; }
        public void setPod(String pod) { this.pod = pod; }
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class Wind {
        public double speed;
        public int deg;
        public double gust;

        // Getters and setters
        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }

        public int getDeg() { return deg; }
        public void setDeg(int deg) { this.deg = deg; }

        public double getGust() { return gust; }
        public void setGust(double gust) { this.gust = gust; }
    }

    public static class Main {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public int pressure;
        public int humidity;

        // Getters and setters
        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        public double getFeelsLike() { return feels_like; }
        public void setFeelsLike(double feels_like) { this.feels_like = feels_like; }

        public double getTempMin() { return temp_min; }
        public void setTempMin(double temp_min) { this.temp_min = temp_min; }

        public double getTempMax() { return temp_max; }
        public void setTempMax(double temp_max) { this.temp_max = temp_max; }

        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }

        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }
}
