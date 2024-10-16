/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsonnews;

/**
 *
 * @author ethan
 */
public class SearchParameters {
    private String engine;
    private String q;
    private String google_domain;
    private String device;

    public SearchParameters(String engine, String q, String google_domain, String device) {
        this.engine = engine;
        this.q = q;
        this.google_domain = google_domain;
        this.device = device;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getGoogle_domain() {
        return google_domain;
    }

    public void setGoogle_domain(String google_domain) {
        this.google_domain = google_domain;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    
    
}
