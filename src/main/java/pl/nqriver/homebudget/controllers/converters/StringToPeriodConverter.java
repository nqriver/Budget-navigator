package pl.nqriver.homebudget.controllers.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.enums.RecurringExpensePaymentPeriod;

@Component
public class StringToPeriodConverter implements Converter<String, RecurringExpensePaymentPeriod> {

    @Override
    public RecurringExpensePaymentPeriod convert(String source) {
        return RecurringExpensePaymentPeriod.valueOf(source.toUpperCase());
    }
}
