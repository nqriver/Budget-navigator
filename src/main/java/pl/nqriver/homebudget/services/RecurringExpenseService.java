package pl.nqriver.homebudget.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.mappers.RecurringExpenseMapper;
import pl.nqriver.homebudget.repositories.RecurringExpenseRepository;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringExpenseService {
    private final RecurringExpenseRepository recurringExpenseRepository;
    private final UserLogInfoService userLogInfoService;
    private final RecurringExpenseMapper recurringExpenseMapper;

    public List<RecurringExpenseDto> getAllRecurringExpenses() {
        UserEntity loggedUserEntity = userLogInfoService.getLoggedUserEntity();
        return recurringExpenseMapper.fromEntitiesToDtos(
                recurringExpenseRepository.findAllByUser(loggedUserEntity));
    }

}
