package com.cbfacademy.apiassessment.reports.Enumeration;

/*  Enumeration Category file containing 
    predefined constants (three categories) with displayName fields for human readability.*/
public enum Category {
    REGULATIONS("Uk Sustainability Regulations"),
    CORPORATE_INITIATIVES("UK Corporate Sustainability Initiatives"),
    BEST_PRACTICES("Uk Case Studies and Best Practices");

    private final String displayName;

    Category(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
