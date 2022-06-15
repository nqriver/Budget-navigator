package pl.nqriver.homebudget.services.dtos;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;

import java.math.BigDecimal;

@Builder
@Data
public class RecurringExpenseResponse implements OwnedEntity {
    private Long id;
    private BigDecimal amount;
    private ExpenseCategory category;
    private Short month;
    private Short day;
    private OwnerDto ownerDto;
}
