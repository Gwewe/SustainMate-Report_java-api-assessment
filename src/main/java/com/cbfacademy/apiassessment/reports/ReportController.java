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
            logGer.error("An unexpected error occured while retrieving reports matching the keyword.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }


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
            logGer.error("An unexpected error occured while creating the report.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }

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
            logGer.error("An unexpected error occured while updating the report with ID {}.", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        
        }
    }


    //To delete a report.
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            logGer.error("No report found with id: {}, failed to delete the report.", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
        logGer.error("An error occurred while deleting the report with id: {}", id, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

    