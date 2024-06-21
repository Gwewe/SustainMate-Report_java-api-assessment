package com.cbfacademy.apiassessment.reports;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository <Report, Long> {
    
    
    /*                  MAIN Method
    This method will search for report based on a keyword.
    */
    @Query("SELECT r FROM Report r WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Report> searchDescriptionByKeyword (String keyword);

    
    /*          Additional method
    This method will find reports based on their category.
    */

    List<Report> findByCategory (Category category);
   

}
