package pl.nqriver.homebudget.services.dtos;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class RecurringExpenseRequest {
    @Positive
    private BigDecimal amount;

    private ExpenseCategory category;

    @Min(1)
    @Max(12)
    private Short month;

    @NotNull
    @Min(1)
    @Max(31)
    private Short day;
}
