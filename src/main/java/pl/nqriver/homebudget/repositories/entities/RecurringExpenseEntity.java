package pl.nqriver.homebudget.repositories.entities;

import lombok.*;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.repositories.entities.converters.CustomMonthConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Month;

@Entity
@Table(name = "recurring_expenses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecurringExpenseEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @Column(columnDefinition = "smallint")
    @Convert(converter = CustomMonthConverter.class)
    private Month month;

    @NotNull
    private Short day;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private UserEntity user;
}
