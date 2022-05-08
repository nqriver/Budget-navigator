package pl.nqriver.homebudget.services.dtos;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class ExpenseDto {
    private Long id;
    private BigDecimal amount;
    private Instant expenseDate;
    private ExpenseCategory category;
}
