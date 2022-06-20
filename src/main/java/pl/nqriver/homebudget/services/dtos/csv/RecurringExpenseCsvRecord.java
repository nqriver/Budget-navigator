package pl.nqriver.homebudget.services.dtos.csv;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class RecurringExpenseCsvRecord extends CsvRecord {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private ExpenseCategory category;
    @NotNull
    private Short month;
    @NotNull
    private Short day;

    @Override
    public List<String> getPropertiesAsList() {
        return List.of(
                id.toString(),
                amount.toString(),
                category.name(),
                month.toString(),
                day.toString()
        );
    }

    @Override
    public List<String> getHeader() {
        return List.of(
                "ID",
                "AMOUNT",
                "CATEGORY",
                "MONTH",
                "DAY"
        );
    }
}
