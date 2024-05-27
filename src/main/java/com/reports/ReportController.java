package com.reports;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/generateReport")
    public ResponseEntity<String> generateReport() {
        try {
            String templatePath = "src/main/resources/report_template.jrxml";
            String outputFilePath = "src/main/resources/ReservationReport.xlsx";

            reportService.generateReport(templatePath, outputFilePath);

            return ResponseEntity.ok("Report generated successfully at " + outputFilePath);
        } catch (JRException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate report");
        }
    }
}
