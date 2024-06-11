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
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.reports.Enumeration.Category;



@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    // Following best practice and creating a separate logger instance for Report controller.
    private static final Logger logGer = LoggerFactory.getLogger(ReportController.class); 

    //Report controller constructor.
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    //To get all the reports.
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        try {
            List<Report> reports = reportService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            logGer.error("None of the reports were found.");
            return ResponseEntity.notFound().build();
        }
    }

    //To find report by their id.
    @GetMapping("/{id}")
    public ResponseEntity<Report> findReportById(@PathVariable Long id){
        Optional<Report> reportOptional = reportService.findReportById(id);
        try {
            if (reportOptional.isPresent()){
                return ResponseEntity.ok(reportOptional.get());
            } else {
                System.out.println("There is no report associated to this id: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logGer.error("The report with this id was not found.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //To get alls the reports by their category.
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Report>> getAllReportByCategory(@PathVariable Category category) {
        try {
            List<Report> reportsbyCategory = reportService.getAllReportByCategory(category);
            return ResponseEntity.ok(reportsbyCategory);
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logGer.error("An unexpected error occurred while retrieving reports", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //To create the report.
    @PostMapping
    public ResponseEntity<Report> createReport (@RequestBody Report report) {
        try {
            Report createdReport = reportService.createReport(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
        } catch (IllegalArgumentException e){
            logGer.error("Failed to create the report", e);
            return ResponseEntity.badRequest().build();
        }
    }

    //To update the report.
    @PutMapping
    public ResponseEntity<Report> updateReport (@PathVariable Long id, @RequestBody Report updatedReport){
        try {
            Report reportUpdated = reportService.updateReport(id, updatedReport);
            return ResponseEntity.ok(reportUpdated);
        } catch (NoSuchElementException e) {
            logGer.error("Initial report not found, failed to update the report.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedReport);
        }
    }


    //To delete a report.
    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> deleteReport(@PathVariable Long id){
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            logGer.error("An error occured while deleting the report {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
    