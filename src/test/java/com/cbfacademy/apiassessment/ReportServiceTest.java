package com.cbfacademy.apiassessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private Report report1, report2;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(ReportRepository.class);
        service = new ReportServiceImpl(mockRepository);
        report1 = new Report("www.url1.co.uk", "Description1", Category.BEST_PRACTICES, Instant.now());
        report2 = new Report("www.url2.co.uk", "Description2", Category.REGULATIONS, Instant.now());
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
        Mockito.when(mockRepository.save(Mockito.any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(mockRepository.findById(report1.getId())).thenReturn(Optional.of(report1));
        service.createReport(report1);
        Optional<Report> optionalReport = service.findReportById(report1.getId());
        Report found = optionalReport.orElseThrow(() -> new NoSuchElementException("No report with this Id was found"));
        
        assertEquals(report1.getId(), found.getId());
    }
    
}
