/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplenewswithmaps;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jsonnews.RelatedQuestion;
import jsonnews.SearchResponse;
import serpapi.GoogleSearch;
import serpapi.SerpApiSearchException;

/**
 *
 * @author ethan
 */
public class NewRelatedQuestionGen {
    Map<String, String> parameter = new HashMap<>();

    // Modified to return a list of RelatedQuestion objects
    public List<RelatedQuestion> callNewsApi(String city) {
        List<RelatedQuestion> relatedQuestions = new ArrayList<>();
        
        parameter.put("engine", "google_news");
        parameter.put("q", "weather+" + city);
        parameter.put("api_key", "73a47df760218e57b1b41fc5a8d831f8c0a38ef14b8e4d873f449ee4058d6668");

        GoogleSearch search = new GoogleSearch(parameter);

        try {
            JsonObject results = search.getJson();
            String json = results.toString();
            SearchResponse response = new Gson().fromJson(json, SearchResponse.class);

            relatedQuestions = response.getRelated_questions(); // Get the list of related questions
            
        } catch (SerpApiSearchException ex) {
            System.out.println("Exception:");
            System.out.println(ex.toString());
        }
        
        return relatedQuestions;
}

}
