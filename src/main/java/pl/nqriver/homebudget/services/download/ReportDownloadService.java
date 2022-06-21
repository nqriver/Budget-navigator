package pl.nqriver.homebudget.services.download;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.enums.RecurringExpensePaymentPeriod;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.services.AssetsService;
import pl.nqriver.homebudget.services.CsvReportService;
import pl.nqriver.homebudget.services.ExpenseService;
import pl.nqriver.homebudget.services.dtos.AssetDto;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;
import pl.nqriver.homebudget.services.dtos.csv.AssetCsvRecord;
import pl.nqriver.homebudget.services.dtos.csv.ExpenseCsvRecord;
import pl.nqriver.homebudget.services.dtos.csv.RecurringExpenseCsvRecord;
import pl.nqriver.homebudget.services.download.RecurringExpenseReportStrategy;
import pl.nqriver.homebudget.services.download.RecurringExpenseReportHandlerRegistry;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportDownloadService {
    private final AssetsService assetsService;
    private final ExpenseService expenseService;
    private final CsvReportService csvReportService;
    private final AssetsMapper assetsMapper;
    private final ExpensesMapper expenseMapper;
    private final RecurringExpenseReportHandlerRegistry recurringExpenseReportHandlerRegistry;


    public ByteArrayInputStream writeAssetsReportToCsv() {
        List<AssetDto> allAssets = assetsService.getAllAssets();
        List<AssetCsvRecord> allAssetCsvRecords = allAssets.stream().map(assetsMapper::fromDtoToCsvRecord)
                .collect(Collectors.toList());
        return csvReportService.writeAsCsv(allAssetCsvRecords);
    }

    public ByteArrayInputStream writeExpenseReportToCsv() {
        List<ExpenseDto> allExpenses = expenseService.getAllExpenses();
        List<ExpenseCsvRecord> allExpensesCsvRecords = allExpenses.stream().map(expenseMapper::fromDtoToCsvRecord)
                .collect(Collectors.toList());
        return csvReportService.writeAsCsv(allExpensesCsvRecords);
    }

    public ByteArrayInputStream reportRecurringExpenses(RecurringExpensePaymentPeriod period) {
        RecurringExpenseReportStrategy reportStrategy =
                recurringExpenseReportHandlerRegistry
                        .getReportFor(period)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown period type"));
        List<RecurringExpenseCsvRecord> report = reportStrategy.report();
        return csvReportService.writeAsCsv(report);
    }
}
