package com.cbfacademy.apiassessment.reports;

/*  Enumeration Category file that contained 
    predefined constants with displayName fields for more human readable*/
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
