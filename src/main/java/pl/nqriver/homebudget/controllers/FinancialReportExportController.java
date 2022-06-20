package pl.nqriver.homebudget.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.homebudget.services.FinancialReportExportService;

import java.io.ByteArrayInputStream;

@RestController
public class FinancialReportExportController {
    private final FinancialReportExportService reportExportService;

    public FinancialReportExportController(FinancialReportExportService reportExportService) {
        this.reportExportService = reportExportService;
    }

    @RequestMapping("/assets/report")
    public ResponseEntity<InputStreamResource> getAssetsReport() {
        String filename = "assets.csv";
        InputStreamResource file =
                new InputStreamResource(reportExportService.writeAssetsReportToCsv());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
    @RequestMapping("/expenses/report")
    public ResponseEntity<InputStreamResource> getExpensesReport() {
        String filename = "expenses.csv";
        InputStreamResource file =
                new InputStreamResource(reportExportService.writeExpenseReportToCsv());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @RequestMapping("/expenses/recurring/report")
    public ResponseEntity<InputStreamResource> getMonthlyRecurringExpensesReport() {
        String filename = "recurring_expenses.csv";
        ByteArrayInputStream stream = reportExportService.reportMonthlyRecurringExpenses();
        InputStreamResource file =
                new InputStreamResource(stream);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
