/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsonnews;

import java.util.List;

/**
 *
 * @author ethan
 */
public class SearchResponse {
    private SearchMetadata search_metadata;
    private SearchParameters search_parameters;
    private SearchInformation search_information;
    private List<RelatedQuestion> related_questions;

    public SearchResponse(SearchMetadata search_metadata, SearchParameters search_parameters, SearchInformation search_information, List<RelatedQuestion> related_questions) {
        this.search_metadata = search_metadata;
        this.search_parameters = search_parameters;
        this.search_information = search_information;
        this.related_questions = related_questions;
    }

    public SearchMetadata getSearch_metadata() {
        return search_metadata;
    }

    public void setSearch_metadata(SearchMetadata search_metadata) {
        this.search_metadata = search_metadata;
    }

    public SearchParameters getSearch_parameters() {
        return search_parameters;
    }

    public void setSearch_parameters(SearchParameters search_parameters) {
        this.search_parameters = search_parameters;
    }

    public SearchInformation getSearch_information() {
        return search_information;
    }

    public void setSearch_information(SearchInformation search_information) {
        this.search_information = search_information;
    }

    public List<RelatedQuestion> getRelated_questions() {
        return related_questions;
    }

    public void setRelated_questions(List<RelatedQuestion> related_questions) {
        this.related_questions = related_questions;
    }
    
    
}
