package pl.nqriver.homebudget.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.exceptions.ReportGenerationException;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import java.io.*;
import java.util.List;

@Service
public class FinancialReportExportService {
    private final AssetsService assetsService;

    public FinancialReportExportService(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    public ByteArrayInputStream writeAssetsAuditToCsv() {

        List<AssetDto> allAssets = assetsService.getAllAssets();
        CSVFormat format = CSVFormat.Builder
                .create()
                .setHeader()
                .setDelimiter(';')
                .build();
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)
        ) {
            for (AssetDto assetDto : allAssets) {
                csvPrinter.printRecord(
                        assetDto.getId(),
                        assetDto.getIncomeDate(),
                        assetDto.getCategory(),
                        assetDto.getAmount()
                );
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException exception) {
            throw new ReportGenerationException("Failed to export income data to csv file\n" + exception.getMessage());
        }
    }
}
