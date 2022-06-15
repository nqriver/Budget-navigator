package pl.nqriver.homebudget.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.services.RecurringExpenseService;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseRequest;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseUpdateRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/expenses/recurring")
public class RecurringExpenseController {

    private final RecurringExpenseService recurringExpenseService;

    public RecurringExpenseController(RecurringExpenseService recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @GetMapping
    public List<RecurringExpenseResponse> getAllRecurringExpenses() {
        return recurringExpenseService.getAllRecurringExpenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecurringExpenseResponse> getRecurringExpense(@PathVariable Long id) {
        return ResponseEntity.ok(recurringExpenseService.getRecurringExpense(id));
    }

    @PostMapping
    public ResponseEntity<RecurringExpenseResponse> createRecurringExpense(
            @RequestBody @Valid RecurringExpenseRequest recurringExpenseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recurringExpenseService.createRecurringExpense(recurringExpenseRequest));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecurringExpense(@PathVariable Long id,
                                                         @RequestBody @Valid RecurringExpenseUpdateRequest recurringExpenseRequest) {
        recurringExpenseService.updateRecurringExpense(id, recurringExpenseRequest);
        return ResponseEntity.ok().build();
    }
}
