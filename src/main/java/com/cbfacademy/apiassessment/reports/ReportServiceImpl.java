package com.cbfacademy.apiassessment.reports;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

import com.cbfacademy.apiassessment.reports.Enumeration.Category;

@Service
public class ReportServiceImpl implements ReportService {

    private final List<Report> reports = new ArrayList<>();
    private final ReportRepository reportRepository;
    private final Category category;

    
    public ReportServiceImpl(ReportRepository reportRepository, Category category){
        this.reportRepository = reportRepository;
        this.category = category;
    }

    @Override
    public List<Report> getAllReports() {
        try {
            if (reports == null || reports.isEmpty()) {
                throw new NoSuchElementException("The reports list is empty or null.");
            } else {
                return reports;
            }
        } catch (RuntimeException e){
            System.err.println("An error occured while retrieving all the reports, " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional <Report> findReportById (Long id){
        try {
            if (id == null || id <= 0){
                throw new IllegalArgumentException(id + ": The id of the report is null or incorrect.");
            } else {
                return reportRepository.findById(id);
            }
        } catch (RuntimeException e) {
            System.err.println("An error occured while retrieving the report by its id, " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Report> getAllReportByCategory (Category category) {
        try{
            List<Report> reportsByCategory = reportRepository.findByCategory(category);
            if(reportsByCategory.isEmpty()){
                throw new NoSuchElementException("There were no reports found in this specific category.");
            } else {
                return reportsByCategory;  
            }
        } catch (RuntimeException e){
            System.err.println("An error occured while trying to access the category, "+ e.getMessage());
            return new ArrayList<>();
        }
            
    }

    @Override
    public Report createReport(Report report){
        reports.add(report);
        report.setDateCreated(LocalDateTime.now());
        report.setCategory(category);
        try {
            reportRepository.save(report);
            return report;
        } catch (IllegalArgumentException e) {
            System.err.println("An error occured while creating the report, "+ e.getMessage());
            return null;
        }
    }

    @Override
    public Report updateReport(Long id, Report updatedReport){
        try {
            Report report = reportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The report was not found."));
            report.setCategory(updatedReport.getCategory());
            report.setUrl(updatedReport.getUrl());
            report.setDescription(updatedReport.getDescription());
            updatedReport.setDateUpdated(LocalDateTime.now());
            return reportRepository.save(report);
        } catch (IllegalArgumentException e) {
            System.err.println("An error occured while updating the report, " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteReport(Long id) {
        try {
            Report report = reportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The report was not found."));
            reportRepository.delete(report);
        } catch (RuntimeException e){
            System.err.println("An error occured while deleting the report, "+ e.getMessage());
            //e.printStackTrace();
        }
    }



}