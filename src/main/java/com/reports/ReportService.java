package com.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ReportService {

    public void generateReport(String templatePath, String outputFilePath) throws JRException, IOException {
        // Generate random data
        List<Map<String, Object>> data = generateRandomData();

        // Compile Jasper report
        JasperReport jasperReport = JasperCompileManager.compileReport(templatePath);

        // Create a JRDataSource from the data
        JRDataSource dataSource = new JRBeanCollectionDataSource(data);

        // Fill the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

        // Export the report to Excel format
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFilePath));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setOnePagePerSheet(true);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setWhitePageBackground(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        // Format the Excel sheet to RTL using Apache POI
        formatExcelToRTL(outputFilePath);
    }

    private void formatExcelToRTL(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        sheet.setRightToLeft(true);

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
        fis.close();
    }

    private List<Map<String, Object>> generateRandomData() {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> row = new HashMap<>();
            row.put("name", generateRandomName());
            row.put("number", generateRandomNumber());
            data.add(row);
        }
        return data;
    }

    private String generateRandomNumber() {
        return String.valueOf(new Random().nextInt(1000));
    }

    private String generateRandomName() {
        String[] names = {"محمد", "علي", "أحمد", "حسين", "فاطمة"};
        return names[new Random().nextInt(names.length)];
    }
}
