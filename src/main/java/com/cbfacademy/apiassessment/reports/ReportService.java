package com.cbfacademy.apiassessment.reports;

import java.util.List;

public interface ReportService {

    
    /**
     *
     * @return A list of all reports.
     */
    List<Report> getAllsReports();


    /**
     * Retrieve Report by its ID.
     *
     * @param id The ID of the Report to retrieve.
     * @return The Report with the specified ID, or null if not found.
     */
    Report getReportById (Long id);

    /**
     * Retrieve all Reports by its category.
     *
     * @param category The category of the Report to retrieve.
     * @return All report with the specified category, or null if not found.
     */
    Report getAllReportByCategory (Category category);

    /**
     * Create a new report.
     *
     * @param report The report object to create.
     * @return The created Report.
     */
    Report createReport(Report report);

    List<Report> searchByCategory(Category category);


}