package pl.nqriver.homebudget.services.download;

import pl.nqriver.homebudget.enums.RecurringExpensePaymentPeriod;
import pl.nqriver.homebudget.services.dtos.csv.RecurringExpenseCsvRecord;

import java.util.List;

public interface RecurringExpenseReportStrategy {
    List<RecurringExpenseCsvRecord> report();
    RecurringExpensePaymentPeriod supportedPeriod();
}
