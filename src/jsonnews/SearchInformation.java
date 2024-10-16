/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsonnews;

/**
 *
 * @author ethan
 */
public class SearchInformation {
    private String query_displayed;
    private long total_results;
    private double time_taken_displayed;
    private String organic_results_state;

    public SearchInformation(String query_displayed, long total_results, double time_taken_displayed, String organic_results_state) {
        this.query_displayed = query_displayed;
        this.total_results = total_results;
        this.time_taken_displayed = time_taken_displayed;
        this.organic_results_state = organic_results_state;
    }

    public String getQuery_displayed() {
        return query_displayed;
    }

    public void setQuery_displayed(String query_displayed) {
        this.query_displayed = query_displayed;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public double getTime_taken_displayed() {
        return time_taken_displayed;
    }

    public void setTime_taken_displayed(double time_taken_displayed) {
        this.time_taken_displayed = time_taken_displayed;
    }

    public String getOrganic_results_state() {
        return organic_results_state;
    }

    public void setOrganic_results_state(String organic_results_state) {
        this.organic_results_state = organic_results_state;
    }
    
    
}
