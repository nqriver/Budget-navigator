package pl.nqriver.homebudget.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.homebudget.services.FinancialReportExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FinancialReportExportController {
    private final FinancialReportExportService reportExportService;

    public FinancialReportExportController(FinancialReportExportService reportExportService) {
        this.reportExportService = reportExportService;
    }

    @RequestMapping(path = "/assets/report", produces = "text/csv")
    public ResponseEntity<InputStreamResource> getAssetsReport(HttpServletResponse response) throws IOException {
        String filename = "assets.csv";
        InputStreamResource file =
                new InputStreamResource(reportExportService.writeAssetsAuditToCsv());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
