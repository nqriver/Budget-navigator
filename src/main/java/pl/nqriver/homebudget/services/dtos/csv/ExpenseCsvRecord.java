package pl.nqriver.homebudget.services.dtos.csv;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ExpenseCsvRecord extends CsvRecord{
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDateTime expenseDate;
    @NotNull
    private ExpenseCategory category;


    @Override
    public List<String> getPropertiesAsList() {
        return List.of(
            id.toString(),
            amount.toString(),
            expenseDate.toString(),
            category.toString()
        );
    }

    @Override
    public List<String> getHeader() {
        return List.of(
                "ID",
                "AMOUNT",
                "EXPENSE DATE",
                "CATEGORY"
        );
    }
}
