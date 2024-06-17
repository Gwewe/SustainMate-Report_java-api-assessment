package com.cbfacademy.apiassessment.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final List<Report> reports = new ArrayList<>();
    private final ReportRepository reportRepository;

    
    public ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
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
                throw new NoSuchElementException (id + ": No report with this Id was found.");
            } else {
                return reportRepository.findById(id);
            }
        } catch (RuntimeException e) {
            System.err.println("An error occured while retrieving the report by its id, " + e.getMessage());
            return Optional.empty();
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
        try {
            return reportRepository.save(report);
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
        }
    }

    
    //Implementing the search by keyword linear search algorithm.
    public List<Report> searchByKeyword (String wordToFind){
        List<Report> matchingReport = new ArrayList<>();
        String[] keywords = wordToFind.toLowerCase().split("[,\\.\\s]");
        try{
            for (Report report: reports){
                String lowercaseDesc = report.getDescription().toLowerCase();
                boolean wordsPresent = true;

                for(String keyword : keywords ){
                    if (!lowercaseDesc.contains(keyword)){
                        wordsPresent = false;
                        break;
                    }
                }
                if (wordsPresent){
                    matchingReport.add(report);
                }
            }
        } catch(RuntimeException e){
            System.err.println("An error occured while searching for the keyword "+ e.getMessage());
        }
        return matchingReport;                  
    }

}
