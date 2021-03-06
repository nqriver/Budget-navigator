package pl.nqriver.homebudget.services.integrations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.repositories.AssetsRepository;
import pl.nqriver.homebudget.repositories.ExpenseRepository;
import pl.nqriver.homebudget.repositories.RecurringExpenseRepository;
import pl.nqriver.homebudget.repositories.UserRepository;
import pl.nqriver.homebudget.repositories.entities.AssetEntity;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.RecurringExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.AssetsService;
import pl.nqriver.homebudget.services.ExpenseService;
import pl.nqriver.homebudget.services.RecurringExpenseService;
import pl.nqriver.homebudget.services.UserDetailsServiceImpl;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Properties;


@SpringBootTest
@Transactional
@WithMockUser(username = "user12345", password = "123username")
public abstract class IntegrationTestDatabaseInitializer {

    public static final String FIRST_USERNAME = "user12345";
    public static final String FIRST_USER_PASSWORD = "123username";
    public static final String SECOND_USERNAME = "user123";
    public static final String SECOND_USER_PASSWORD = "1234username";

    @Autowired
    protected AssetsRepository assetsRepository;
    @Autowired
    protected AssetsService assetsService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    protected ExpenseService expenseService;
    @Autowired
    protected ExpenseRepository expenseRepository;
    @Autowired
    protected UserDetailsServiceImpl userDetailsService;
    @Autowired
    protected RecurringExpenseRepository recurringExpenseRepository;
    @Autowired
    protected RecurringExpenseService recurringExpenseService;


    protected UserEntity initDefaultUserInDatabase() {
        var user = UserEntity.builder()
                .username(FIRST_USERNAME)
                .password(FIRST_USER_PASSWORD)
                .build();
        return userRepository.save(user);
    }

    protected UserEntity initSecondUserInDatabase() {
        var user = UserEntity.builder()
                .username(SECOND_USERNAME)
                .password(SECOND_USER_PASSWORD)
                .build();
        return userRepository.save(user);
    }


    protected ExpenseEntity initExpenseOfUserInDatabase(UserEntity userEntity) {
        var expense = ExpenseEntity.builder()
                .amount(BigDecimal.ONE)
                .category(ExpenseCategory.INSURANCE)
                .expenseDate(LocalDateTime.now())
                .user(userEntity)
                .build();
        return expenseRepository.save(expense);
    }

    protected ExpenseEntity initExpenseOfUserWithDate(UserEntity userEntity, LocalDateTime dateTime) {
        var expense = ExpenseEntity.builder()
                .amount(BigDecimal.ONE)
                .category(ExpenseCategory.INSURANCE)
                .expenseDate(dateTime)
                .user(userEntity)
                .build();
        return expenseRepository.save(expense);
    }

    void initExpensesWithUserAndDates(UserEntity userEntity, List<LocalDateTime> dates) {
        dates.forEach(e -> initExpenseOfUserWithDate(userEntity, e));
    }

    protected void initDatabaseWithDefaultUserAndAssets() {
        UserEntity userEntity = initDefaultUserInDatabase();
        AssetEntity assetEntityOne = AssetEntity.builder()
                .amount(BigDecimal.ONE)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity assetEntityTwo = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.LOAN_RETURNED)
                .user(userEntity)
                .build();
        AssetEntity assetEntityThree = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(List.of(
                assetEntityOne,
                assetEntityTwo,
                assetEntityThree
        ));
    }


    protected void initDatabaseWithSecondUserAndAssets() {
        UserEntity userEntity = initSecondUserInDatabase();
        AssetEntity assetEntityOne = AssetEntity.builder()
                .amount(BigDecimal.ONE)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();
        AssetEntity assetEntityTwo = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.LOAN_RETURNED)
                .user(userEntity)
                .build();
        AssetEntity assetEntityThree = AssetEntity
                .builder()
                .amount(BigDecimal.TEN)
                .incomeDate(Instant.now())
                .category(AssetCategory.OTHER)
                .user(userEntity)
                .build();

        assetsRepository.saveAll(List.of(
                assetEntityOne,
                assetEntityTwo,
                assetEntityThree
        ));
    }

    protected void initDatabaseWithRecurringExpense(UserEntity userEntity) {
        var entity = RecurringExpenseEntity.builder()
                .month(Month.MAY)
                .day(Short.MAX_VALUE)
                .amount(BigDecimal.TEN)
                .category(ExpenseCategory.HOUSING)
                .user(userEntity)
                .build();
        recurringExpenseRepository.save(entity);
    }



}
