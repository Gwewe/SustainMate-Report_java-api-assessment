package com.cbfacademy.apiassessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cbfacademy.apiassessment.reports.Category;
import com.cbfacademy.apiassessment.reports.Report;
import com.cbfacademy.apiassessment.reports.ReportRepository;
import com.cbfacademy.apiassessment.reports.ReportServiceImpl;
import com.cbfacademy.apiassessment.reports.ReportService;

public class ReportServiceTest {
    private ReportService service;
    private ReportRepository mockRepository;
    private Report report1, report2, report3, report4;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ReportRepository.class);
        service = new ReportServiceImpl(mockRepository);
        report1 = new Report("www.url1.co.uk", "Description1", Category.BEST_PRACTICES, Instant.now());
        report2 = new Report("www.url2.co.uk", "Description2", Category.REGULATIONS, Instant.now());
        report3 = new Report("www.url3.com", "Description3", Category.BEST_PRACTICES, Instant.now());
        report4 = new Report("www.url4.co.uk", "Description4", Category.REGULATIONS, Instant.now());
    }

    @Test
    void testGetAllReportsInitiallyEmpty() {
        Mockito.when(mockRepository.findAll()).thenReturn(List.of());
        assertTrue(service.getAllReports().isEmpty());
    }

    @Test
    void testGetAllReportsAfterAddingThem(){
        Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(report1, report2));
        service.createReport(report1);
        service.createReport(report2);
        List<Report> reports = service.getAllReports();

        assertEquals(2, reports.size());
        assertTrue(reports.contains(report1));
        assertTrue(reports.contains(report2));
    }

    @Test
    void testFindExistingReportById() {
        // Mock save method to return the report that was passed.
        Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Mock findById method to return Optional that has report1 when report1 Id was passed.
        Mockito.when(mockRepository.findById(report1.getId())).thenReturn(Optional.of(report1));
        // saving the report.
        service.createReport(report1);
        // Retrieve the report by it's id.
        Optional<Report> optionalReport = service.findReportById(report1.getId());
        //check if report with the id was found if not throw nosuchelementexception.
        Report found = optionalReport.orElseThrow(() -> new NoSuchElementException(report1.getId() + "No report with this Id was found"));
       
        // check if report found had the id expected.
        assertEquals(report1.getId(), found.getId());
    }

    @Test
    void testFindNonExistingReportById() {
        //non existing id.
        Long nonExistingId = 999L;
        //mock the findbyId method to return optional empty when nonexistingId was passed.
        Mockito.when(mockRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            service.findReportById(nonExistingId).orElseThrow(() -> new NoSuchElementException(nonExistingId + ": No report with this Id was found.")); 
        });

        assertEquals(nonExistingId + ": No report with this Id was found.", exception.getMessage());

    }

    @Test
    void testGetAllReportByCategory(){
        // Mock the findbycategory method to return the list of reports for Regulations and best practices categories.
        Mockito.when(mockRepository.findByCategory(Category.REGULATIONS)).thenReturn(List.of(report2, report4));
        Mockito.when(mockRepository.findByCategory(Category.BEST_PRACTICES)).thenReturn(List.of(report1, report3));

        // retrieve the best practices reports and check if the list has the correct size and content.
        List<Report> bestPracticesReports = service.getAllReportByCategory(Category.BEST_PRACTICES);
        assertEquals(2, bestPracticesReports.size());
        assertTrue(bestPracticesReports.contains(report1));
        assertTrue(bestPracticesReports.contains(report3));

        //retrieve the regulations reports and check if the list has the correct size and content.
        List<Report> regulationsReports = service.getAllReportByCategory(Category.REGULATIONS);
        assertEquals(2, regulationsReports.size());
        assertTrue(regulationsReports.contains(report2));
        assertTrue(regulationsReports.contains(report4));


    }
    
}
