/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package favouritesOriginal;

/**
 *
 * @author Bruneez
 */
public class Favourites {

    private String city_name;
    private String city_code;
    private int user_id;
    private int favId;
    

     public Favourites() {
      
     }
    
    public Favourites(String city_name, int user_id) {
        this.city_name = city_name;
        this.user_id = user_id;
    }

    public Favourites(String city_name, int user_id, int favId) {
        this.city_name = city_name;
        this.user_id = user_id;
        this.favId = favId;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public int getFavId() {
        return favId;
    }
    
    

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    

    @Override
    public String toString() {
        return "FavId= " + favId + " User_id= " + user_id + "\nCity_name=" + city_name;
    }

}
