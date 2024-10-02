/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package favourites;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import simpleusers.DBConnection;

/**
 *
 * @author Bruneez
 */
public class CrudOperator {

    private Connection conn;
    PreparedStatement pstmt = null;

    public CrudOperator() {
        try {

            conn = new DBConnection().getConn();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
        }
    } // end constructor

    public void insertFavourite(Favourites fav) throws SQLException {
        String insertSql = "INSERT INTO Favourites (USER_ID,CITY_NAME) VALUES (?, ?)";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(insertSql);
            // Set the city_name value
            pstmt.setInt(1, fav.getUser_id());// Set the user_id value
            pstmt.setString(2, fav.getCity_name());

            pstmt.executeUpdate();
            System.out.println("Favourite inserted successfully.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            throw e; // rethrow the exception to be handled at a higher level
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Unexpected Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
    }//end insert method

    public void removeFavourite(int favID) throws SQLException {
        String deleteString = "DELETE FROM Favourites Where FAVOURITE_ID = ?";

        try {
         
            pstmt = conn.prepareStatement(deleteString);
            pstmt.setInt(1, favID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//end of remove method

    public void updateFavourite(int faveId, String cityName) {

        cityName = cityName.replace(" ", " ");
        String updateString = "UPDATE Favourites SET City_Name = ? WHERE FAVOURITE_ID = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(updateString);
            pstmt.setString(1, cityName);
            pstmt.setInt(2, faveId);

            //Execute update
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        } //end of finnaly
    } // end of method

    public List<Favourites> read() {
        List<Favourites> favs = new ArrayList<>();
        String sql = "SELECT * FROM Favourites";
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                String cityName = rs.getString("City_name");
                int userId = rs.getInt("User_id");
                int favId = rs.getInt("Favourite_ID");

                favs.add(new Favourites(cityName, userId, favId));

            }
        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, sqlE.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }
        return favs;
    }

//    
//     public List<Favourites> read() {
//        List<Favourites> favs = new ArrayList<>();
//        String sql = "SELECT * FROM Favourites";
//        ResultSet rs = null;
//
//        try {
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                //java.sql.Timestamp createdAt = rs.getTimestamp("city_added_at");
//                //String createdAtStr;
////                if (createdAt != null) {
////                    createdAtStr = createdAt.toString();
////                } else {
////                    createdAtStr = null;
////                }
//                String cityName = rs.getString("City_name");
//                int userId = rs.getInt("User_id");
//              
//                favs.add(new Favourites(cityName, userId));
//
//            }
//        } catch (SQLException sqlE) {
//            JOptionPane.showMessageDialog(null, sqlE.getMessage());
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//            } catch (Exception exception) {
//                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//        return favs;
//    }
//    
//  public void removeFavourite(int Favorites_Id) throws SQLException {
//        String deleteString = "DELETE FROM Favourites Where user_id = Favourites_id values(?)";
//
//        try {
//            int favID = 0;
//            pstmt = conn.prepareStatement(deleteString);
//            pstmt.setInt(1,favID);
//            pstmt.executeUpdate();
//            System.out.println("Favourite deleted successfully.");
//        } catch (SQLException e) {
//            System.out.println("SQL Error: " + e.getMessage());
//        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
//                } catch (Exception exception) {
//                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        }
//
//    }
}
