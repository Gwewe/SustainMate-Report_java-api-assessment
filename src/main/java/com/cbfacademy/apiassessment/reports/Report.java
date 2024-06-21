package com.cbfacademy.apiassessment.reports;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @Column(name = "dateCreated", columnDefinition = "DATETIME(6)")
    private Instant dateCreated;

    //Default constructor
    public Report() {}

    //Constructor with the other fields
    public Report( String url, String description, Category category, Instant dateCreated) {
        this.url = url;
        this.description = description;
        this.category = category;
        this.dateCreated = dateCreated;
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

    public Instant getDateCreated(){
        return dateCreated;
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

    public void setDateCreated (Instant dateCreated){
        this.dateCreated = dateCreated;
    }
}
