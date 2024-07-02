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
     * Retrieve Report by its category.
     *
     * @param category The category of the Report to retrieve.
     * @return The Report with the specified category, or optional empty if not found.
     */
    Optional <List<Report>> findByCategory(Category category);

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

    /**
     * Will search for reports based on a simple keyword that the user would input.
     *
     * @param keyword The keyword to search for in the descriptions of reports.
     * @return A list of reports that contain the keyword in their description.
     */
    List<Report> searchDescriptionByKeyword(String keyword);

    /**
     * Linear search for reports based on multiple keywords or sentence.
     *
     * @param wordToFind The string containing keywords or phrases separated by commas, dots, or spaces.
     * @return A list of reports that match all keywords or phrases in their description.
     */
    List<Report> linearSearchDescriptionByKeywords(String wordToFind);

}
