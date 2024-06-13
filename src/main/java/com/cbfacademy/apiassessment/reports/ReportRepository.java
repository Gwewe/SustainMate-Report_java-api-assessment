package com.cbfacademy.apiassessment.reports;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository <Report, Long> {
    
    
    /*                  MAIN Method
    This method will search for report based on a keyword.
    */
    //List<Report> searchByKeyword (String keyword);

    
    /*          Additional method
    This method will find reports based on their category.
    */

    List<Report> findByCategory (Category category);
   

}
