package com.cbfacademy.apiassessment.reports;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository <Report, Long> {
    
    
    /**                  
    * This method will search for reports based on a simple keyword the user would input.
    *
    *@param keyword to search for in the the description of all reports.
    *@return a list of reports that contain the keyword in their description.
    */
    @Query("SELECT r FROM Report r WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Report> searchDescriptionByKeyword (String keyword);

    
    /**          
    * This method will find reports based on their category.
    *
    *@param category the category to seach for reports.
    *@return a list of reports that belong to the specified category.
    */
    List<Report> findByCategory (Category category);

}
