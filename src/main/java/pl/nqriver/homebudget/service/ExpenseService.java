package pl.nqriver.homebudget.service;

import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repository.ExpenseRepository;
import pl.nqriver.homebudget.repository.entities.ExpenseEntity;
import pl.nqriver.homebudget.repository.entities.UserEntity;
import pl.nqriver.homebudget.service.dto.AssetDto;
import pl.nqriver.homebudget.service.dto.ExpenseDto;

import java.util.List;

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

    public ExpenseEntity setExpense(ExpenseDto expenseDto) {
        UserEntity loggedUser = userLogInfoService.getLoggedUserEntity();
        var expenseEntity = expensesMapper.fromDtoToEntity(expenseDto, loggedUser);
        return expenseRepository.save(expenseEntity);
    }
}
