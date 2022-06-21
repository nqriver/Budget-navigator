package pl.nqriver.homebudget.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.homebudget.enums.RecurringExpensePaymentPeriod;
import pl.nqriver.homebudget.services.download.ReportDownloadService;

import java.io.ByteArrayInputStream;

@RestController
public class FinancialReportExportController {
    private final ReportDownloadService reportExportService;

    public FinancialReportExportController(ReportDownloadService reportExportService) {
        this.reportExportService = reportExportService;
    }

    @GetMapping("/assets/download")
    public ResponseEntity<InputStreamResource> getAssetsReport() {
        String filename = "assets.csv";
        InputStreamResource file =
                new InputStreamResource(reportExportService.writeAssetsReportToCsv());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/expenses/download")
    public ResponseEntity<InputStreamResource> getExpensesReport() {
        String filename = "expenses.csv";
        InputStreamResource file =
                new InputStreamResource(reportExportService.writeExpenseReportToCsv());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/expenses/recurring/download")
    public ResponseEntity<InputStreamResource> getMonthlyRecurringExpensesReport(
            @RequestParam("period") RecurringExpensePaymentPeriod period) {
        String filename = "recurring_expenses.csv";
        ByteArrayInputStream stream = reportExportService.reportRecurringExpenses(period);
        InputStreamResource file = new InputStreamResource(stream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
