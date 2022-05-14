package pl.nqriver.homebudget.services;

import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.ExpensesMapper;
import pl.nqriver.homebudget.repositories.ExpenseRepository;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    public static final String FROM_DATE_SUFIX = "T00:00:00.001Z";
    public static final String TO_DATE_SUFIX = "T00:23:59.999Z";

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

    public List<ExpenseDto> findAllBetweenDates(String fromDate, String toDate) {
        UserEntity loggedUserEntity = userLogInfoService.getLoggedUserEntity();
        Instant from = Instant.parse(fromDate + FROM_DATE_SUFIX);
        Instant to = Instant.parse(toDate + TO_DATE_SUFIX);
        return expenseRepository.getAllByUserAndExpenseDateBetween(loggedUserEntity, from, to)
                .stream()
                .map(expensesMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
