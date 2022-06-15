package pl.nqriver.homebudget.services.dtos;


import lombok.*;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecurringExpenseUpdateRequest {

    @Positive
    private BigDecimal amount;
}
