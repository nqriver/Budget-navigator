package pl.nqriver.homebudget.mappers;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.repositories.entities.RecurringExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.OwnerDto;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseRequest;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;

import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RecurringExpenseMapper {

    public RecurringExpenseEntity fromRequestToEntity(RecurringExpenseRequest expenseDto, UserEntity user) {
        Month monthValue = Objects.isNull(expenseDto.getMonth()) ? null : Month.of(expenseDto.getMonth());
        return RecurringExpenseEntity.builder()
                .amount(expenseDto.getAmount())
                .category(expenseDto.getCategory())
                .user(user)
                .day(expenseDto.getDay())
                .month(monthValue)
                .build();
    }

    public RecurringExpenseRequest fromEntityToDto(RecurringExpenseEntity expenseEntity) {
        Short monthValue = Objects.isNull(expenseEntity.getMonth()) ?
                null : (short) expenseEntity.getMonth().getValue();
        return RecurringExpenseRequest.builder()
                .amount(expenseEntity.getAmount())
                .day(expenseEntity.getDay())
                .month(monthValue)
                .category(expenseEntity.getCategory())
                .build();
    }

    public List<RecurringExpenseResponse> fromEntitiesToDtos(List<RecurringExpenseEntity> expenseEntities) {
        return expenseEntities.stream()
                .map(this::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    public RecurringExpenseResponse fromEntityToResponse(RecurringExpenseEntity expenseEntity) {
        Short monthValue = Objects.isNull(expenseEntity.getMonth()) ?
                null : (short) expenseEntity.getMonth().getValue();
        return RecurringExpenseResponse.builder()
                .id(expenseEntity.getId())
                .amount(expenseEntity.getAmount())
                .day(expenseEntity.getDay())
                .ownerDto(OwnerDto
                        .builder()
                        .username(expenseEntity.getUser().getUsername())
                        .build()
                )
                .month(monthValue)
                .category(expenseEntity.getCategory())
                .build();
    }
}
