package com.cbfacademy.apiassessment.reports;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    
    /**
     *
     * @return A list of all reports, or an empty list if not found.
     */
    List<Report> getAllReports();


    /**
     * Retrieve Report by its ID.
     *
     * @param id The ID of the Report to retrieve.
     * @return The Report with the specified ID, or null if not found.
     */
    Optional <Report> findReportById (Long id);

    /**
     * Retrieve all Reports by its category.
     *
     * @param category The category of the Report to retrieve.
     * @return All report with the specified category, or empty list if not found.
     */
    List<Report> getAllReportByCategory (Category category);

    /**
     * Create a new report.
     *
     * @param report The report object to create.
     * @return The created Report, or null if object was not created.
     */
    Report createReport(Report report);

    /**
     * Update the existing Report by its ID.
     *
     * @param id         The ID of the Report to update.
     * @param updatedReport The updated Report object.
     * @return The updated Report, or null if the ID is not found.
     */
    Report updateReport(Long id, Report updatedReport);

    /**
     * Delete The Report by its ID.
     *
     * @param id The ID of the report to delete.
     */
    void deleteReport(Long id);

}
