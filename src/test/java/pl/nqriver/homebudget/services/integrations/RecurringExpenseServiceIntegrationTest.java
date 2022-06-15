package pl.nqriver.homebudget.services.integrations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.repositories.entities.RecurringExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseRequest;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;

import java.math.BigDecimal;
import java.util.List;

class RecurringExpenseServiceIntegrationTest extends IntegrationTestDatabaseInitializer {


    @Test
    void shouldSaveOneRecurringExpenseInDatabase() {
        //given
        UserEntity userEntity = initDefaultUserInDatabase();

        RecurringExpenseRequest request = RecurringExpenseRequest.builder()
                .amount(BigDecimal.valueOf(233))
                .category(ExpenseCategory.ENTERTAINMENTS)
                .month((short) 12)
                .day((short) 12)
                .build();

        //when
        RecurringExpenseResponse recurringExpense = recurringExpenseService.createRecurringExpense(request);

        //then
        List<RecurringExpenseEntity> savedExpenses = recurringExpenseRepository.findAll();
        Assertions.assertThat(savedExpenses).hasSize(1);
        RecurringExpenseEntity savedEntity = savedExpenses.get(0);

        Assertions.assertThat(savedEntity.getAmount()).isEqualTo(request.getAmount());
        Assertions.assertThat(savedEntity.getDay()).isEqualTo(request.getDay());
        Assertions.assertThat(savedEntity.getMonth().getValue()).isEqualTo(request.getMonth().intValue());
        Assertions.assertThat(savedEntity.getCategory()).isEqualTo(request.getCategory());
    }
}