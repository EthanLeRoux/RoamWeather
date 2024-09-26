package simplereviews2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import simpleusers.DBConnection;

public class ReviewDAO {

    private DBConnection db;
    private Connection connection;

    public ReviewDAO() throws SQLException {
        db = new DBConnection();
        connection = db.getConn();
    }

    public void addReview(Review review, String reviewCity) throws SQLException {
        String sql = "INSERT INTO REVIEWS (USER_ID, REVIEW_CONTENT, REVIEW_CITY, REVIEW_RATING) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, review.getUserId());
            statement.setString(2, review.getReviewContent());
            statement.setString(3, reviewCity);
            statement.setInt(4, review.getReviewRating());
            statement.executeUpdate();
        }
    }

    public Review getReviewById(int reviewId) throws SQLException {
        String sql = "SELECT * FROM REVIEWS WHERE REVIEW_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reviewId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("USER_ID");
                    String reviewContent = resultSet.getString("REVIEW_CONTENT");
                    String reviewCity = resultSet.getString("REVIEW_CITY");
                    int reviewRating = resultSet.getInt("REVIEW_RATING");
                    return new Review(userId, reviewCity, reviewContent, reviewRating);
                }
            }
        }
        return null;
    }

    public int getReviewId(int userId, String reviewContent) throws SQLException {
        String sql = "SELECT review_id FROM REVIEWS WHERE user_id = ? AND review_content = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, reviewContent);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("review_id");
                } else {
                    throw new SQLException("No review found with the given user_id and review_content");
                }
            }
        }
    }

    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM REVIEWS";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("USER_ID");
                String reviewContent = resultSet.getString("REVIEW_CONTENT");
                String reviewCity = resultSet.getString("REVIEW_CITY");
                int reviewRating = resultSet.getInt("REVIEW_RATING");
                reviews.add(new Review(userId, reviewCity, reviewContent, reviewRating));
            }
        }
        return reviews;
    }
    public List<Review> getCities() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT review_city FROM REVIEWS";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
               
                String reviewCity = resultSet.getString("REVIEW_CITY");
                reviews.add(new Review( reviewCity));
            }
        }
        return reviews;
    }

    public void updateReview(int reviewId, String newContent, String newCity, int newRating) throws SQLException {
        String sql = "UPDATE REVIEWS SET REVIEW_CONTENT = ?, REVIEW_CITY = ?, "
                + "REVIEW_RATING = ?, REVIEW_UPDATED_AT = ? WHERE REVIEW_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newContent);
            statement.setString(2, newCity);
            statement.setInt(3, newRating);
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            statement.setInt(5, reviewId);
            statement.executeUpdate();
        }
    }

    public void deleteReview(int reviewId) throws SQLException {
        String sql = "DELETE FROM REVIEWS WHERE REVIEW_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reviewId);
            statement.executeUpdate();
        }
    }

    public List<Review> getReviewsByCity(String cityName) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM REVIEWS WHERE REVIEW_CITY = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cityName);  // Filter by city
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("USER_ID");
                    String reviewContent = resultSet.getString("REVIEW_CONTENT");
                    String reviewCity = resultSet.getString("REVIEW_CITY");
                    int reviewRating = resultSet.getInt("REVIEW_RATING");
                    reviews.add(new Review(userId, reviewCity, reviewContent, reviewRating));  // Create Review object
                }
            }
        }
        return reviews;  // Return only the reviews for the specific city
    }
}
