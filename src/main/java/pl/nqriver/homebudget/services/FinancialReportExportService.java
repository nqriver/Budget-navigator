package pl.nqriver.homebudget.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.mappers.RecurringExpenseMapper;
import pl.nqriver.homebudget.mappers.RecurringExpenseToCsvMapper;
import pl.nqriver.homebudget.services.dtos.AssetDto;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;
import pl.nqriver.homebudget.services.dtos.csv.AssetCsvRecord;
import pl.nqriver.homebudget.services.dtos.csv.ExpenseCsvRecord;
import pl.nqriver.homebudget.services.dtos.csv.RecurringExpenseCsvRecord;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FinancialReportExportService {
    private final AssetsService assetsService;
    private final ExpenseService expenseService;
    private final CsvReportService csvReportService;
    private final RecurringExpenseToCsvMapper recurringExpenseMapper;
    private final RecurringExpenseService recurringExpenseService;
    private final AssetsMapper assetsMapper;
    private final ExpensesMapper expenseMapper;


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

    public ByteArrayInputStream reportMonthlyRecurringExpenses() {
        List<RecurringExpenseResponse> allExpenses = recurringExpenseService.getAllRecurringExpenses();
        List<RecurringExpenseCsvRecord> allExpensesCsvRecords =
                allExpenses
                        .stream()
                        .filter(e -> Objects.nonNull(e.getMonth()))
                        .map(recurringExpenseMapper::responseToCsvRecord)
                        .collect(Collectors.toList());
        return csvReportService.writeAsCsv(allExpensesCsvRecords);
    }
    public ByteArrayInputStream reportAnnualRecurringExpenses() {
        List<RecurringExpenseResponse> allExpenses = recurringExpenseService.getAllRecurringExpenses();
        List<RecurringExpenseCsvRecord> allExpensesCsvRecords =
                allExpenses
                        .stream()
                        .filter(e -> Objects.isNull(e.getMonth()))
                        .map(recurringExpenseMapper::responseToCsvRecord)
                        .collect(Collectors.toList());
        return csvReportService.writeAsCsv(allExpensesCsvRecords);
    }
}
