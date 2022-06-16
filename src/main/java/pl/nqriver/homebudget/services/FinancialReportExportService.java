package pl.nqriver.homebudget.services;

import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.AssetsMapper;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.services.dtos.AssetDto;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;
import pl.nqriver.homebudget.services.dtos.csv.AssetCsvRecord;
import pl.nqriver.homebudget.services.dtos.csv.ExpenseCsvRecord;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialReportExportService {
    private final AssetsService assetsService;
    private final ExpenseService expenseService;
    private final CsvReportService csvReportService;
    private final AssetsMapper assetsMapper;
    private final ExpensesMapper expenseMapper;

    public FinancialReportExportService(AssetsService assetsService,
                                        ExpenseService expenseService,
                                        CsvReportService csvReportService,
                                        AssetsMapper assetsMapper,
                                        ExpensesMapper expenseMapper) {
        this.assetsService = assetsService;
        this.expenseService = expenseService;
        this.csvReportService = csvReportService;
        this.assetsMapper = assetsMapper;
        this.expenseMapper = expenseMapper;
    }

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
}
