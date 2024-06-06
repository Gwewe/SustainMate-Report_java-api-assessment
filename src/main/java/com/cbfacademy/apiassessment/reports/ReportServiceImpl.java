package com.cbfacademy.apiassessment.reports;

import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final List<Report> reports = new ArrayList<>();
    private ReportRepository reportRepository;

    @Override
    public List<Report> getAllsReports() {
        try {
            if (reports == null || reports.isEmpty()) {
                throw new NoSuchElementException("The reports list was not found.");
            }
            return new ArrayList<>(reports);
        } catch (RuntimeException e){
            System.err.println("An error occured while retrieving all the reports " + e.getMessage());
            e.printStackTrace();
            return null;

        }
            

        
    }


    public Report getReportById (Long id){}


    public Report getAllReportByCategory (Category category)throws EnumConstantNotPresentException{}


    public Report createReport(Report report){}

    public Report updateReport(Long id, Report updatedReport){}

    @Override
    public void deleteReport(Long id) {
        try {
            Report report = reportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The report was not found."));
            reportRepository.delete(report);
        } catch (RuntimeException e){
            System.err.println("An error occured while deleting the report"+ e.getMessage());
            e.printStackTrace();
        }
    }



}
