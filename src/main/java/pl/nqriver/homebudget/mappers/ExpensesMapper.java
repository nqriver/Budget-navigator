package pl.nqriver.homebudget.mappers;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repository.entities.ExpenseEntity;
import pl.nqriver.homebudget.repository.entities.UserEntity;
import pl.nqriver.homebudget.service.dto.ExpenseDto;

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

    public List<ExpenseDto> fromEntitiesToDtos(List<ExpenseEntity> expenseEntities) {
        return expenseEntities.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
