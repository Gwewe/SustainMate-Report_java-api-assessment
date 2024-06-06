package com.cbfacademy.apiassessment.reports;
import java.util.List;

/*  ReportRepository interface defining operations to managed report in the system. */
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository <Report, Long> {
    
    
    /*                  MAIN Method
        This method will find reports based on their category.
    */
    List<Report> findByCategory (Category category);
    
    
    
    /*          Additional method (for future developement)

        This method will find reports where the url field containing given string match.
    */
    List<Report> findByUrlContaining(String url);

    /*       Additional method (for future developement)

        This method will find all reports associated to an Url 
        where the description fields containing the given string match.
    */
    List<Report> findByDescriptionContaining(String description);

   

}
