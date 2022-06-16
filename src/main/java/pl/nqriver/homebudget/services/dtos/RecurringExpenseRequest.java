package pl.nqriver.homebudget.services.dtos;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;

import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Month;

@Data
@Builder
public class RecurringExpenseRequest {
    @Positive
    private BigDecimal amount;

    private ExpenseCategory category;

    @Valid
    private Month month;

    @NotNull
    @Min(1)
    @Max(31)
    private Short day;
}
