/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weathertoday;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Today {
    public class Clouds{
    public int all;
}

public class Coord{
    public double lon;
    public double lat;
}

public class Main{
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;
    public int sea_level;
    public int grnd_level;
}

public class Rain{
    @JsonProperty("1h") 
    public double _1h;
}

public class Root{
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}

public class Sys{
    public int type;
    public int id;
    public String country;
    public int sunrise;
    public int sunset;
}

public class Weather{
    public int id;
    public String main;
    public String description;
    public String icon;
}

public class Wind{
    public double speed;
    public int deg;
    public double gust;
}

}
