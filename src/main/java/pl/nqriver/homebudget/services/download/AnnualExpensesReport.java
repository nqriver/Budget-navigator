package pl.nqriver.homebudget.services.download;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.enums.RecurringExpensePaymentPeriod;
import pl.nqriver.homebudget.mappers.RecurringExpenseToCsvMapper;
import pl.nqriver.homebudget.services.RecurringExpenseService;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;
import pl.nqriver.homebudget.services.dtos.csv.RecurringExpenseCsvRecord;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnnualExpensesReport implements RecurringExpenseReportStrategy {

    private final RecurringExpenseService recurringExpenseService;
    private final RecurringExpenseToCsvMapper recurringExpenseMapper;

    @Override
    public List<RecurringExpenseCsvRecord> report() {
        List<RecurringExpenseResponse> allExpenses = recurringExpenseService.getAllRecurringExpenses();
        return allExpenses
                .stream()
                .filter(e -> Objects.isNull(e.getMonth()))
                .map(recurringExpenseMapper::responseToCsvRecord)
                .collect(Collectors.toList());
    }

    @Override
    public RecurringExpensePaymentPeriod supportedPeriod() {
        return RecurringExpensePaymentPeriod.ANNUAL;
    }
}
