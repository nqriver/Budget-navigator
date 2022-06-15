package pl.nqriver.homebudget.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import pl.nqriver.homebudget.exceptions.ResourceNotFoundException;
import pl.nqriver.homebudget.mappers.RecurringExpenseMapper;
import pl.nqriver.homebudget.repositories.RecurringExpenseRepository;
import pl.nqriver.homebudget.repositories.entities.RecurringExpenseEntity;
import pl.nqriver.homebudget.repositories.entities.UserEntity;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseRequest;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseUpdateRequest;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringExpenseService {
    private final RecurringExpenseRepository recurringExpenseRepository;
    private final UserLogInfoService userLogInfoService;
    private final RecurringExpenseMapper recurringExpenseMapper;

    public List<RecurringExpenseResponse> getAllRecurringExpenses() {
        UserEntity loggedUserEntity = userLogInfoService.getLoggedUserEntity();
        return recurringExpenseMapper.fromEntitiesToDtos(
                recurringExpenseRepository.findAllByUser(loggedUserEntity));
    }

    @Transactional
    public RecurringExpenseResponse createRecurringExpense(RecurringExpenseRequest recurringExpenseRequest) {
        UserEntity loggedUser = userLogInfoService.getLoggedUserEntity();
        RecurringExpenseEntity recurringExpense = recurringExpenseMapper.fromRequestToEntity(recurringExpenseRequest, loggedUser);
        recurringExpenseRepository.save(recurringExpense);
        return recurringExpenseMapper.fromEntityToResponse(recurringExpense);
    }


    @Transactional
    public void updateRecurringExpense(Long id, RecurringExpenseUpdateRequest recurringExpenseRequest) {
        UserEntity loggedUser = userLogInfoService.getLoggedUserEntity();
        RecurringExpenseEntity entityToUpdate = recurringExpenseRepository.findByIdAndUser(id, loggedUser).orElseThrow(
                () -> new AccessDeniedException("User " + loggedUser.getUsername() + " has no access to resource of id" + id)
        );
        entityToUpdate.setAmount(recurringExpenseRequest.getAmount());
    }

    @PostAuthorize("hasPermission(returnObject, null)")
    public RecurringExpenseResponse getRecurringExpense(Long id) {
        RecurringExpenseEntity expenseEntity = recurringExpenseRepository.findById(id).orElseThrow(
                ResourceNotFoundException::new
        );
        return recurringExpenseMapper.fromEntityToResponse(expenseEntity);
    }
}
