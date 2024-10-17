package simplenewswithmaps;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import jsonnews.RelatedQuestion;
import simpleusers.RunSimpleUsers;

public class ArticleController {
    @FXML
    private ListView<String> listNews;

    @FXML
    private ListView<String> myListNews;

    private ObservableList<RelatedQuestion> relatedQuestionsList = FXCollections.observableArrayList();
    private ObservableList<String> listView2Items = FXCollections.observableArrayList();

    public void initialize() throws FileNotFoundException, IOException {
        NewRelatedQuestionGen newsHandler = new NewRelatedQuestionGen();
        //FileReader fr = FileReader("city.txt");
        FileReader fr = new FileReader("city.txt");
        BufferedReader br = new BufferedReader(fr);
        String city = "Cape Town";
        if(br.readLine()!=null){
           city = br.readLine();
        }
        List<RelatedQuestion> relatedQuestions = newsHandler.callNewsApi(city);

        // Clear and populate relatedQuestionsList
        relatedQuestionsList.clear();
        relatedQuestionsList.addAll(relatedQuestions);

        // Convert the RelatedQuestion objects to a string representation and populate listNews
        ObservableList<String> items = FXCollections.observableArrayList();
        for (RelatedQuestion question : relatedQuestions) {
            items.add(question.getQuestion()); // Use getQuestion() or other methods to get the desired string
        }

        // Set the items in the ListView
        listNews.setItems(items);

        // Set the second ListView with an empty list initially
        myListNews.setItems(listView2Items);
    }

    @FXML
    private void addSelectedItemToListView2() throws SQLException {
        int selectedIndex = listNews.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            RelatedQuestion selectedQuestion = relatedQuestionsList.get(selectedIndex);
            System.out.println("Selected Question: " + selectedQuestion.getQuestion()); // Debug
            listView2Items.add(selectedQuestion.getQuestion()); // Add to the second ListView

            // Optionally remove from the first list
            relatedQuestionsList.remove(selectedIndex); 
            listNews.getItems().remove(selectedIndex); // Remove from the first ListView
            NewsDAO news = new NewsDAO();
            news.addArticle(selectedQuestion, selectedIndex);
            System.out.println("Added to Table!");
        } else {
            System.out.println("No item selected in listNews.");
        }
    }
    
    @FXML
    private void deleteSelectedItemFromListView2() {
        int selectedIndex = myListNews.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedQuestionString = myListNews.getItems().get(selectedIndex);
            listView2Items.remove(selectedIndex); // Remove from the second ListView

            // Optionally, re-add to the first list
            //relatedQuestionsList.add(new RelatedQuestion(selectedQuestionString)); // Adjust this line based on RelatedQuestion constructor

            // Update listNews if necessary
            ObservableList<String> items = FXCollections.observableArrayList();
            for (RelatedQuestion question : relatedQuestionsList) {
                items.add(question.getQuestion());
            }
            listNews.setItems(items);
        } else {
            System.out.println("No item selected in myListNews.");
        }
    }
    
    @FXML
    public void switchSceneHome() throws IOException {
        RunSimpleUsers rs = new RunSimpleUsers();
        rs.switchScene("/home/HomePage.fxml");
    }
    
    public void openWebpage(String url) {
    try {
        new ProcessBuilder("x-www-browser", url).start();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    public static void openUrl(String url) {
        // Check if Desktop is supported on the platform
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                // Create a URI from the URL string
                URI uri = new URI(url);
                // Open the URL in the default web browser
                desktop.browse(uri);
            } catch (URISyntaxException e) {
                System.out.println("Invalid URL: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error opening URL: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop is not supported on this platform.");
        }
    }
    
    
@FXML
void readArticle() {
    int selectedIndex = listNews.getSelectionModel().getSelectedIndex();
    if (selectedIndex != -1) {
        RelatedQuestion selectedQuestion = relatedQuestionsList.get(selectedIndex);
        String articleLink = selectedQuestion.getLink();
        
        if (articleLink != null && !articleLink.isEmpty()) {
            openUrl(articleLink);  // Opens the selected article link in the default browser
        } else {
            System.out.println("No link available for this article.");
        }
    } else {
        System.out.println("No article selected.");
    }
}


}

