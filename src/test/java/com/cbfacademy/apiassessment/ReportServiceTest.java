package com.cbfacademy.apiassessment;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.cbfacademy.apiassessment.reports.Category;
import com.cbfacademy.apiassessment.reports.Report;
import com.cbfacademy.apiassessment.reports.ReportRepository;
import com.cbfacademy.apiassessment.reports.ReportService;
import com.cbfacademy.apiassessment.reports.ReportServiceImpl;

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
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(report1, report2, report3, report4));
        service.createReport(report1);
        service.createReport(report2);
        service.createReport(report3);
        service.createReport(report4);
        List<Report> reports = service.getAllReports();

        assertEquals(4, reports.size());
        assertTrue(reports.contains(report1));
        assertTrue(reports.contains(report2));
        assertTrue(reports.contains(report3));
        assertTrue(reports.contains(report4));
        
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

        //When attempting to retrieve non-existing reports by Id, it will throw a NoSuchElementException.
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            service.findReportById(nonExistingId).orElseThrow(() -> new NoSuchElementException(nonExistingId + ": No report with this Id was found.")); 
        });

        //check the exception message.
        assertEquals(nonExistingId + ": No report with this Id was found.", exception.getMessage());

    }

    @Test
    void testGetAllExistingReportByCategory(){
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

    @Test
    void testGetNonExistingReportByCategory(){
        //Mock the findbycategory method to return the empty list of the corporate initiatives.
        Mockito.when(mockRepository.findByCategory(Category.CORPORATE_INITIATIVES)).thenReturn(Collections.emptyList());

        //WWhen attempting to retrieve non-existing reports from a category, it will throw a NoSuchElementException..
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            service.getAllReportByCategory(Category.CORPORATE_INITIATIVES);
        });
        
        //check the exception message.
        assertEquals("No reports were found in this specific category.", exception.getMessage());
    }


    @Test
    void testCreateReport () {
        //Mock the save method to return new report that was passed in with random long id.
        Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> {
            Report report = invocation.getArgument(0);
            Long randomId = Math.abs(ThreadLocalRandom.current().nextLong());
            ReflectionTestUtils.setField(report, "id", randomId);

            return report;
        });

        Report created = service.createReport(report1);

        assertNotNull(created.getId());
        assertEquals(report1.getUrl(), created.getUrl());
        assertEquals(report1.getDescription(), created.getDescription());
        assertEquals(report1.getCategory(), created.getCategory());
    }

    @Test
    void testUpdateExistingReport (){
        Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(mockRepository.findById(report1.getId())).thenReturn(Optional.of(report1));
        service.createReport(report1);
        Report updatedReport = new Report("www.updatedurl1.co.uk", "UpdatedDescription", Category.BEST_PRACTICES, Instant.now());
        updatedReport.setUrl("www.updatedurl1.co.uk");
        Report updated = service.updateReport(report1.getId(), updatedReport);

        assertNotNull(updated);
        assertEquals("www.updatedurl1.co.uk", updatedReport.getUrl());
    }

    @Test
    void testUpdateNonExistingReport () {
        Long nonExistingId = 999L;

        Mockito.when(mockRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            service.updateReport(nonExistingId, report3);
        });

        assertEquals("The report was not found.", exception.getMessage());
    }


    @Test
    void testDeleteExistingReport () {
        Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(report1, report2, report3, report4));
        service.createReport(report1);
        service.createReport(report2);
        service.createReport(report3);
        service.createReport(report4);

        assertTrue(service.getAllReports().contains(report1));
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(report2, report3, report4));
        Mockito.when(mockRepository.findById(report1.getId())).thenReturn(Optional.of(report1));
        service.deleteReport(report1.getId());

        assertFalse(service.getAllReports().contains(report1));

    }
    
}
