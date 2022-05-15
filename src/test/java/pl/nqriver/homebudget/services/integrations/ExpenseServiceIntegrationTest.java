package pl.nqriver.homebudget.services.integrations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class ExpenseServiceIntegrationTest extends IntegrationTestDatabaseInitializer {

    @Autowired
    ExpensesMapper expensesMapper;

    @Test
    void getAllExpensesBetweenDates() {
        // given
        LocalDateTime fromDate = LocalDateTime.of(2022, Month.MAY, 3, 0, 1);
        LocalDateTime inRangeDate = LocalDateTime.of(2022, Month.MAY, 6, 0, 1);
        LocalDateTime notInRangeDate = LocalDateTime.of(2022, Month.MAY, 17, 0, 1);
        LocalDateTime toDate = LocalDateTime.of(2022, Month.MAY, 14, 0, 1);

        String from = fromDate.toLocalDate().toString();
        String to = fromDate.toLocalDate().toString();


        var user = initDefaultUserInDatabase();
        initExpensesWithUserAndDates(
                user,
                List.of(fromDate, toDate, inRangeDate, notInRangeDate));
        // when

        List<ExpenseDto> result = expenseService.findAllBetweenDates(from, to);

        // then
        assertThat(result).hasSize(1);
//        List<String> resultDates = result.stream()
//                .map(ExpenseDto::getExpenseDate)
//                .map(e -> e.toString().substring(0, fromDate.length()))
//                .collect(Collectors.toList());
//
//        assertThat(resultDates).hasSize(3)
//                .containsExactly(fromDate, toDate, inRangeDate)
//                .doesNotContain(notInRangeDate);
    }

    @Test
    void shouldSaveOneExpenseToDb() {
        // given
        initDefaultUserInDatabase();
        ExpenseDto expense = ExpenseDto.builder()
                .amount(BigDecimal.ONE)
                .category(ExpenseCategory.ENTERTAINMENTS)
                .expenseDate(LocalDateTime.now())
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
                .expenseDate(LocalDateTime.now())
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