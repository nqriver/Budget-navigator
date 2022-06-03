package pl.nqriver.homebudget.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.exceptions.ResourceNotFoundException;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repositories.ExpenseRepository;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    public static final String FROM_DATE_SUFFIX = "T00:00:01.000";
    public static final String TO_DATE_SUFFIX = "T23:59:59.000";

    private final ExpensesMapper expensesMapper;
    private final ExpenseRepository expenseRepository;
    private final UserLogInfoService userLogInfoService;

    public ExpenseService(ExpensesMapper expensesMapper, ExpenseRepository expenseRepository, UserLogInfoService userLogInfoService) {
        this.expensesMapper = expensesMapper;
        this.expenseRepository = expenseRepository;
        this.userLogInfoService = userLogInfoService;
    }


    public List<ExpenseDto> getAllExpenses() {
        UserEntity loggedUser = userLogInfoService.getLoggedUserEntity();
        return expensesMapper.fromEntitiesToDtos(expenseRepository.getExpenseEntityByUser(loggedUser));
    }

    public ExpenseDto setExpense(ExpenseDto expenseDto) {
        UserEntity loggedUser = userLogInfoService.getLoggedUserEntity();
        var expenseEntity = expensesMapper.fromDtoToEntity(expenseDto, loggedUser);
        var savedExpenseEntity = expenseRepository.save(expenseEntity);
        return expensesMapper.fromEntityToDto(savedExpenseEntity);
    }

    public void deleteExpense(Long id, Authentication authentication) {
        ExpenseEntity expense = expenseRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        if (!isLoggedUserOwnerOfResource(authentication, expense)) {
            throw new AccessDeniedException("User has no access to resource");
        }
        expenseRepository.delete(expense);
    }

    private boolean isLoggedUserOwnerOfResource(Authentication authentication, ExpenseEntity expense) {
        return authentication.getName().equals(expense.getUser().getUsername());
    }

    @Transactional
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        var expenseToUpdate = expenseRepository.findById(id)
                .orElseThrow();
        updateExpenseProperties(expenseDto, expenseToUpdate);
        return expensesMapper.fromEntityToDto(expenseToUpdate);
    }

    private void updateExpenseProperties(ExpenseDto expenseDto, ExpenseEntity expenseToUpdate) {
        if (Objects.nonNull(expenseDto.getCategory())) {
            expenseToUpdate.setCategory(expenseDto.getCategory());
        }
        if (Objects.nonNull(expenseDto.getAmount())) {
            expenseToUpdate.setAmount(expenseDto.getAmount());
        }
        if (Objects.nonNull(expenseDto.getExpenseDate())) {
            expenseToUpdate.setExpenseDate(expenseDto.getExpenseDate());
        }
    }

    public List<ExpenseDto> findAllBetweenDates(String fromDate, String toDate) {
        UserEntity loggedUserEntity = userLogInfoService.getLoggedUserEntity();
        LocalDateTime from = LocalDateTime.parse(fromDate + FROM_DATE_SUFFIX);
        LocalDateTime to = LocalDateTime.parse(toDate + TO_DATE_SUFFIX);
        return expenseRepository.getAllByUserAndExpenseDateBetween(loggedUserEntity, from, to)
                .stream()
                .map(expensesMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public ExpenseDto getExpense(Long id) {
        return expensesMapper.fromEntityToDto(expenseRepository.getById(id));
    }
}
