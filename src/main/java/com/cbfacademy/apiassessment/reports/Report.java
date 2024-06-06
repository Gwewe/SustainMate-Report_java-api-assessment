package com.cbfacademy.apiassessment.reports;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String description;
    private String category;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    //Default constructor
    public Report() {}

    //Constructor with the other fields
    public Report( String title, String url, String description, String category, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.category = category;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    
    // All the Getters
    public Long getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }
    
    public String getUrl(){
        return url;
    }

    public String getDescription(){
        return description;
    }
    
    public String getCategory(){
        return category;
    }

    public LocalDateTime getDateCreated(){
        return dateCreated;
    }

    public LocalDateTime getDateUpdated(){
        return dateUpdated;
    }
    

    // All the Setters
    public void setTitle (String title){
        this.title = title;
    }

    public void setUrl(String url){
        this.url = url;
    }
    
    public void setDescription(String description){
        this.description = description;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setDateCreated (LocalDateTime dateCreated){
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated (LocalDateTime dateUpdated){
        this.dateUpdated = dateUpdated;
    }
}
