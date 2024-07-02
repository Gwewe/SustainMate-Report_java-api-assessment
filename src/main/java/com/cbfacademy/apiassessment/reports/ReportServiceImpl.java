package com.cbfacademy.apiassessment.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    
    public ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> getAllReports() {
        try {
            List<Report> reports = reportRepository.findAll();
            if (reports.isEmpty()) {
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

    //defining findbycategory method
    @Override
    public Optional <List<Report>> findByCategory (Category category) {
        try{
            if (category == null) {
                throw new IllegalArgumentException("Oups the category input can not be null or empty");
            }

            List<Report> reports = reportRepository.findAll();
            List<Report> matchingReportByCategory = new ArrayList<>();

            for (Report report : reports){
                if (report.getCategory() == category) {
                    matchingReportByCategory.add(report);
                }
            }
    
            if(matchingReportByCategory.isEmpty()){
                throw new NoSuchElementException("Oups, No reports were found in this specific category.");
            }
            return Optional.of(matchingReportByCategory);
        } catch (RuntimeException e){
            System.err.println("An error occured while trying to access the category, "+ e.getMessage());
            return Optional.empty();
        }
            
    }

    @Override
    public List<Report> getAllReportByCategory (Category category) {
        try{
            List<Report> reportsByCategory = reportRepository.findByCategory(category);
            if(reportsByCategory.isEmpty()){
                throw new NoSuchElementException("No reports were found in this specific category.");
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
        try {
            List<Report> reports = reportRepository.findAll();
            reports.add(report);
            return reportRepository.save(report);
        } catch (RuntimeException e) {
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
        } catch (RuntimeException e) {
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
            throw e;
        }
    }

    @Override
    // will search report that will matches with a simple keyword.
    public List<Report> searchDescriptionByKeyword(String keyword) {
        try {
            return reportRepository.searchDescriptionByKeyword(keyword);
        } catch (RuntimeException e) {
            System.err.println("An error occurred while searching for the keyword: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    //Implementing the linear search algorithm that will search description to find reports that will match with more complex keywords or sentence.
    public List<Report> linearSearchDescriptionByKeywords (String wordToFind){
        List<Report> matchingReport = new ArrayList<>();
        //Take into consideration keywords that would be split by commas, dot, space and long sentence.
        String[] keywords = wordToFind.toLowerCase().split("[,\\.\\s]+");
        try{
            List<Report> reports = reportRepository.findAll();
            for (Report report: reports){
                String lowercaseDescription = report.getDescription().toLowerCase();
                boolean wordsPresent = true;

                for(String keyword : keywords ){
                    if (!lowercaseDescription.contains(keyword)){
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
