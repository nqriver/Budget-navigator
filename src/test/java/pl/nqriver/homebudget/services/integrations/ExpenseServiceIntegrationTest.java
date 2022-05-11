package pl.nqriver.homebudget.services.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repositories.ExpenseRepository;
import pl.nqriver.homebudget.repositories.UserRepository;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.ExpenseService;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@WithMockUser(username = "user123", password = "123username")
class ExpenseServiceIntegrationTest {

    public static final String FIRST_USERNAME = "user123";
    public static final String FIRST_USER_PASSWORD = "123username";
    public static final String SECOND_USERNAME = "user1234";
    public static final String SECOND_USER_PASSWORD = "1234username";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpensesMapper expensesMapper;
    @Autowired
    ExpenseService expenseService;
    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    void shouldSaveOneExpenseToDb() {
        // given
        initUserInDatabase();
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
        var userEntity = initUserInDatabase();
        var expense = initExpenseInDatabase(userEntity);
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
        var firstUser = initUserInDatabase();
        var secondUser = initSecondUserInDatabase();

        var firstUserExpense = initExpenseInDatabase(firstUser);
        var firstUserExpense2 = initExpenseInDatabase(firstUser);
        var secondUserExpense = initExpenseInDatabase(secondUser);

        //when

        assertThat(expenseRepository.findAll()).hasSize(3);
        List<ExpenseDto> allExpensesOfLoggedUser = expenseService.getAllExpenses();

        // then
        assertThat(allExpensesOfLoggedUser).hasSize(2);
    }

    @Test
    void shouldUpdateExpenseInDatabase() {
        var user = initUserInDatabase();
        var savedExpense = initExpenseInDatabase(user);

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

    private ExpenseEntity initExpenseInDatabase(UserEntity userEntity) {
        var expense = ExpenseEntity.builder()
                .amount(BigDecimal.ONE)
                .category(ExpenseCategory.INSURANCE)
                .expenseDate(Instant.now())
                .user(userEntity)
                .build();
        return expenseRepository.save(expense);
    }


    private UserEntity initUserInDatabase() {
        var user = UserEntity.builder()
                .username(FIRST_USERNAME)
                .password(FIRST_USER_PASSWORD)
                .build();
        return userRepository.save(user);
    }

    private UserEntity initSecondUserInDatabase() {
        var user = UserEntity.builder()
                .username(SECOND_USERNAME)
                .password(SECOND_USER_PASSWORD)
                .build();
        return userRepository.save(user);
    }

}