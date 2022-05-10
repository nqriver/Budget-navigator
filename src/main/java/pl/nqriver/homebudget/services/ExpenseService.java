package pl.nqriver.homebudget.services;

import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repositories.ExpenseRepository;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ExpenseService {
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

    public void deleteExpense(ExpenseDto expenseDto) {
        UserEntity loggedUser = userLogInfoService.getLoggedUserEntity();
        ExpenseEntity expenseToDelete = expensesMapper.fromDtoToEntity(expenseDto, loggedUser);
        expenseRepository.delete(expenseToDelete);
    }

    @Transactional
    public ExpenseDto updateExpense(ExpenseDto expenseDto) {
        var expenseDtoId = expenseDto.getId();
        var expenseToUpdate = expenseRepository.findById(expenseDtoId).orElseThrow();
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

}
