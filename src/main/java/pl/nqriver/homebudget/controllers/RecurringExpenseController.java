package pl.nqriver.homebudget.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.nqriver.homebudget.services.RecurringExpenseService;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseDto;

import java.util.List;

@RestController
@RequestMapping("/expenses/recurring")
public class RecurringExpenseController {

    private final RecurringExpenseService recurringExpenseService;

    public RecurringExpenseController(RecurringExpenseService recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @GetMapping
    public List<RecurringExpenseDto> getAllRecurringExpenses() {
        return recurringExpenseService.getAllRecurringExpenses();
    }
}
