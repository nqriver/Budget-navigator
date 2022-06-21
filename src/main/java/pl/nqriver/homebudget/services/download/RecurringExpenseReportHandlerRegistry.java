package pl.nqriver.homebudget.services.download;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.enums.RecurringExpensePaymentPeriod;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RecurringExpenseReportHandlerRegistry {
    private final List<RecurringExpenseReportStrategy> reportHandlers;

    public Optional<RecurringExpenseReportStrategy> getReportFor(RecurringExpensePaymentPeriod period) {
        return reportHandlers
                .stream()
                .filter(reportHandler -> reportHandler.supportedPeriod().equals(period))
                .findAny();
    }
}
