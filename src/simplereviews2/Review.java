package simplereviews2;

public class Review {
       private int userId;
    private int reviewId;
    private String cityName;
    private String reviewContent; // reviewContent should be initialized
    private int reviewRating;
// If you still need a constructor that doesn't set reviewContent
    // Use a default value for reviewContent if reviewContent is optional
    public Review(int userId, String cityName, int reviewRating) {
        this.userId = userId;
        this.cityName = cityName;
        this.reviewContent = "No content provided"; // Default value for review content
        this.reviewRating = reviewRating;
    }
    // Constructor with all fields
    public Review(int userId, String cityName, String reviewContent, int reviewRating) {
        this.userId = userId;
        this.cityName = cityName;
        this.reviewContent = reviewContent;
        this.reviewRating = reviewRating;
    }

    public Review(String cityName) {
        this.cityName = cityName;
    }

    
    // Getters and setters for the city name
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    // Updated toString() method to include cityName
    @Override
    public String toString() {
        return 
               "  City: " + cityName + "\n" +   
                "  "+reviewContent + "\n" +
               "  Rating: " + reviewRating + "\n" 
               ;
    }
}
