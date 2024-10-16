package simplenewswithmaps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jsonnews.RelatedQuestion;
import simpleusers.DBConnection;

public class NewsDAO {
    private DBConnection conn;

    /**
     * Adds a new article to the database.
     *
     * @param question The RelatedQuestion object containing article details.
     * @param userId The ID of the user associated with the article.
     * @throws SQLException If there is an error with the SQL operation.
     */
    public void addArticle(RelatedQuestion question, int userId) throws SQLException {
        String sql = "INSERT INTO ARTICLES (USER_ID, ARTICLE_TITLE, ARTICLE_SNIPPET, ARTICLE_LINK) VALUES (?, ?, ?, ?)";
        conn = new DBConnection();
        
        try (Connection connection = conn.getConn();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, question.getQuestion()); // Assuming 'getQuestion' returns the title
            pstmt.setString(3, question.getSnippet()); // Assuming 'getSnippet' returns the article snippet
            pstmt.setString(4, question.getLink()); // Assuming 'getLink' returns the link

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding article: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes an article from the database by ID.
     *
     * @param articleId The ID of the article to delete.
     * @throws SQLException If there is an error with the SQL operation.
     */
    public void deleteArticle(int articleId) throws SQLException {
        String sql = "DELETE FROM ARTICLES WHERE ARTICLE_ID = ?";
        conn = new DBConnection();
        
        try (Connection connection = conn.getConn();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, articleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting article: " + e.getMessage());
            throw e;
        }
    }
}
