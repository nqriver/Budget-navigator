package pl.nqriver.homebudget.services.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.validators.annotiations.ExistingExpenseCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ExpenseDto {
    private Long id;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expenseDate;

    @ExistingExpenseCategory
    private ExpenseCategory category;
}
