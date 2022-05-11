package pl.nqriver.homebudget.services.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ExpenseServiceIntegrationTest extends IntegrationTestDatabaseInitializer {

    @Autowired
    ExpensesMapper expensesMapper;

    @Test
    void shouldSaveOneExpenseToDb() {
        // given
        initDefaultUserInDatabase();
        ExpenseDto expense = ExpenseDto.builder()
                .amount(BigDecimal.ONE)
                .category(ExpenseCategory.ENTERTAINMENTS)
                .expenseDate(Instant.now())
                .build();

        // when
        ExpenseDto responseDto = expenseService.setExpense(expense);

        // then
        List<ExpenseEntity> allExpensesOfUser = expenseRepository.findAll();
        assertThat(allExpensesOfUser).hasSize(1);

        assertThat(responseDto.getAmount()).isEqualTo(expense.getAmount());
        assertThat(responseDto.getExpenseDate()).isEqualTo(expense.getExpenseDate());
        assertThat(responseDto.getCategory()).isEqualTo(expense.getCategory());
    }

    @Test
    void shouldDeleteOneExpenseFromDb() {
        // given
        var userEntity = initDefaultUserInDatabase();
        var expense = initExpenseOfUserInDatabase(userEntity);
        var expenseToRemoveDto = expensesMapper.fromEntityToDto(expense);
        assertThat(expenseRepository.findAll()).hasSize(1);

        // when
        expenseService.deleteExpense(expenseToRemoveDto);

        //then
        assertThat(expenseRepository.findById(expense.getId())).isNotPresent();
        assertThat(expenseRepository.findAll()).hasSize(0);
    }

    @Test
    void shouldGetAllExpensesFromDatabase() {
        // given
        var firstUser = initDefaultUserInDatabase();
        var secondUser = initSecondUserInDatabase();

        var firstUserExpense = initExpenseOfUserInDatabase(firstUser);
        var firstUserExpense2 = initExpenseOfUserInDatabase(firstUser);
        var secondUserExpense = initExpenseOfUserInDatabase(secondUser);

        //when

        assertThat(expenseRepository.findAll()).hasSize(3);
        List<ExpenseDto> allExpensesOfLoggedUser = expenseService.getAllExpenses();

        // then
        assertThat(allExpensesOfLoggedUser).hasSize(2);
    }

    @Test
    void shouldUpdateExpenseInDatabase() {
        var user = initDefaultUserInDatabase();
        var savedExpense = initExpenseOfUserInDatabase(user);

        var updatedExpenseDto = ExpenseDto.builder()
                .id(savedExpense.getId())
                .expenseDate(Instant.ofEpochSecond(100))
                .amount(BigDecimal.valueOf(3300))
                .category(ExpenseCategory.PERSONAL_SPENDING)
                .build();

        // when
        expenseService.updateExpense(updatedExpenseDto);

        // then
        var updatedEntity = expenseRepository.findById(savedExpense.getId()).orElseThrow();
        assertThat(updatedEntity.getExpenseDate()).isEqualTo(updatedExpenseDto.getExpenseDate());
        assertThat(updatedEntity.getCategory()).isEqualTo(updatedExpenseDto.getCategory());
        assertThat(updatedEntity.getAmount()).isEqualTo(updatedExpenseDto.getAmount());

    }


}