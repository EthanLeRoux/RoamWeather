/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsonnews;

/**
 *
 * @author ethan
 */
public class RelatedQuestion {
    private String question;
    private String snippet;
    private String title;
    private String link;
    private String displayed_link;
    private String source_logo;
    private String next_page_token;
    private String serpapi_link;

    public RelatedQuestion(String question, String snippet, String title, String link, String displayed_link, String source_logo, String next_page_token, String serpapi_link) {
        this.question = question;
        this.snippet = snippet;
        this.title = title;
        this.link = link;
        this.displayed_link = displayed_link;
        this.source_logo = source_logo;
        this.next_page_token = next_page_token;
        this.serpapi_link = serpapi_link;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDisplayed_link() {
        return displayed_link;
    }

    public void setDisplayed_link(String displayed_link) {
        this.displayed_link = displayed_link;
    }

    public String getSource_logo() {
        return source_logo;
    }

    public void setSource_logo(String source_logo) {
        this.source_logo = source_logo;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public String getSerpapi_link() {
        return serpapi_link;
    }

    public void setSerpapi_link(String serpapi_link) {
        this.serpapi_link = serpapi_link;
    }

    @Override
public String toString() {
    return "RelatedQuestion{" + 
           "question=" + question + ",\n" + 
           "snippet=" + snippet + ",\n" + 
           "title=" + title + ",\n" + 
           "link=" + link + ",\n" + 
           "displayed_link=" + displayed_link + ",\n" + 
           "source_logo=" + source_logo + ",\n" + 
           "next_page_token=" + next_page_token + ",\n" + 
           "serpapi_link=" + serpapi_link + 
           '}' + "\n"+ "\n";
}

    
    
}
