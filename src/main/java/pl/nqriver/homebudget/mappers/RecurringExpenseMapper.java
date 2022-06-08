package pl.nqriver.homebudget.mappers;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repositories.entities.RecurringExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecurringExpenseMapper {

    public RecurringExpenseEntity fromDtoToEntity(RecurringExpenseDto expenseDto, UserEntity user) {
        return RecurringExpenseEntity.builder()
                .amount(expenseDto.getAmount())
                .category(expenseDto.getCategory())
                .user(user)
                .day(expenseDto.getDay())
                .month(expenseDto.getMonth())
                .build();
    }

    public RecurringExpenseDto fromEntityToDto(RecurringExpenseEntity expenseEntity) {
        return RecurringExpenseDto.builder()
                .id(expenseEntity.getId())
                .amount(expenseEntity.getAmount())
                .day(expenseEntity.getDay())
                .month(expenseEntity.getMonth())
                .category(expenseEntity.getCategory())
                .build();
    }

    public List<RecurringExpenseDto> fromEntitiesToDtos(List<RecurringExpenseEntity> expenseEntities) {
        return expenseEntities.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }
}
