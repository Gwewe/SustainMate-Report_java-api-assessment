package com.cbfacademy.apiassessment.reports;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "SustainMate Report API", description = "API used to fetch and manage reports regarding sustainability in the Uk.")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final ReportRepository reportRepository;
    // Following best practice and creating a separate logger instance for Report controller.
    private static final Logger logGer = LoggerFactory.getLogger(ReportController.class); 

    //Report controller constructor.
    public ReportController(ReportService reportService, ReportRepository reportRepository){
        this.reportService = reportService;
        this.reportRepository = reportRepository;
    }

    @Operation(summary = "Get all reports", description = "Returns a list of all reports saved in the database.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of reports."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To get all the reports.
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        try {
            List<Report> reports = reportService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            logGer.error("The reports were not found.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @Operation(summary = "Get the report by their id ", description = "Returns the report saved in the database with the given report Id.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the report with this Id."),
        @ApiResponse(responseCode = "404", description = "The report with this id was not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To find report by their id.
    @GetMapping("/{id}")
    public ResponseEntity<Report> findReportById(@PathVariable Long id){
        try {
            Optional<Report> reportOptional = reportService.findReportById(id);
            return reportOptional.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logGer.error("There is no report associated to this id: {}", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
        } catch (Exception e) {
            logGer.error("The report with this id was not found.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get reports by category", description = "Returns a list of reports that belong to a specific category.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of reports for this category."),
        @ApiResponse(responseCode = "204", description = "Request is correct but no content was found for this category."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To get alls the reports by their category.
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Report>> getAllReportByCategory(@PathVariable Category category) {
        try {
            List<Report> reportsbyCategory = reportService.getAllReportByCategory(category);
            if (reportsbyCategory.isEmpty()){
                logGer.info("No content found for the category {}", category);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(reportsbyCategory);
        } catch (Exception e) {
            logGer.error("An unexpected error occurred while retrieving reports", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Search for reports by keyword", description = "Returns a list of reports that contain a specific keyword in their description.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of reports matching keyword."),
        @ApiResponse(responseCode = "204", description = "No reports found matching the given keyword."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To get the search result of the searchdescriptionbykeyword method
    @GetMapping("/search")
    public ResponseEntity<List<Report>> searchDescriptionByKeyword (@RequestParam String wordToFind){
        try {
            List<Report> matchingReport = reportRepository.searchDescriptionByKeyword(wordToFind);
            if (matchingReport.isEmpty()){
                logGer.info("No content found, no report matching the keyword {}", wordToFind);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(matchingReport);
        } catch (Exception e) {
            logGer.error("An unexpected error occurred while retrieving reports matching the keyword.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }


    @Operation(summary = "Create a new report", description = "Add a new report in the list of reports.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the new report."),
        @ApiResponse(responseCode = "400", description = "Bad request, failed to create the report."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To create the report.
    @PostMapping("/")
    public ResponseEntity<Report> createReport (@RequestBody Report report) {
        try {
            Report createdReport = reportService.createReport(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
        } catch (IllegalArgumentException e){
            logGer.error("Failed to create the report", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logGer.error("An unexpected error occurred while creating the report.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }

    @Operation(summary = "Update the report", description = "Update an existing report using its Id.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the report."),
        @ApiResponse(responseCode = "404", description = "The report the given id was not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To update the report.
    @PutMapping ("/{id}")
    public ResponseEntity<Report> updateReport (@PathVariable Long id, @RequestBody Report updatedReport){
        try {
            Report reportUpdated = reportService.updateReport(id, updatedReport);
            return ResponseEntity.ok(reportUpdated);
        } catch (NoSuchElementException e) {
            logGer.error("No report with ID {} were found, failed to update the report.", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedReport);
        } catch (Exception e) {
            logGer.error("An unexpected error occurred while updating the report with ID {}.", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }


    @Operation(summary = "Delete the report", description = "Delete the report using the report Id.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the report."),
        @ApiResponse(responseCode = "404", description = "The report with the given Id was not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //To delete a report.
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            logGer.error("No report found with id: {}, failed to delete the report.", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
        logGer.error("An error occurred while deleting the report with id: {}", id, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

    