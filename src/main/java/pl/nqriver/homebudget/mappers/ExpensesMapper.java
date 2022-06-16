package pl.nqriver.homebudget.mappers;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repositories.entities.ExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;
import pl.nqriver.homebudget.services.dtos.csv.ExpenseCsvRecord;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpensesMapper {
    public ExpenseEntity fromDtoToEntity(ExpenseDto expenseDto, UserEntity user) {
        return ExpenseEntity.builder()
                .id(expenseDto.getId())
                .amount(expenseDto.getAmount())
                .expenseDate(expenseDto.getExpenseDate())
                .category(expenseDto.getCategory())
                .user(user)
                .build();
    }

    public ExpenseDto fromEntityToDto(ExpenseEntity expenseEntity) {
        return ExpenseDto.builder()
                .id(expenseEntity.getId())
                .amount(expenseEntity.getAmount())
                .expenseDate(expenseEntity.getExpenseDate())
                .category(expenseEntity.getCategory())
                .build();
    }

    public ExpenseCsvRecord fromDtoToCsvRecord(ExpenseDto expenseDto) {
        return ExpenseCsvRecord.builder()
                .id(expenseDto.getId())
                .amount(expenseDto.getAmount())
                .expenseDate(expenseDto.getExpenseDate())
                .category(expenseDto.getCategory())
                .build();
    }

    public List<ExpenseDto> fromEntitiesToDtos(List<ExpenseEntity> expenseEntities) {
        return expenseEntities.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
