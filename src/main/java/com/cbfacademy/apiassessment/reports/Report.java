package com.cbfacademy.apiassessment.reports;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.cbfacademy.apiassessment.reports.Enumeration.Category;

@Entity
@Table(name = "reports")
public class Report {

    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    //Default constructor
    public Report() {}

    //Constructor with the other fields
    public Report( String url, String description, Category category, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
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
    
    public String getUrl(){
        return url;
    }

    public String getDescription(){
        return description;
    }
    
    public Category getCategory(){
        return category;
    }

    public LocalDateTime getDateCreated(){
        return dateCreated;
    }

    public LocalDateTime getDateUpdated(){
        return dateUpdated;
    }
    

    // All the Setters
    public void setUrl(String url){
        this.url = url;
    }
    
    public void setDescription(String description){
        this.description = description;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public void setDateCreated (LocalDateTime dateCreated){
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated (LocalDateTime dateUpdated){
        this.dateUpdated = dateUpdated;
    }
}
