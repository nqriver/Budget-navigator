package pl.nqriver.homebudget.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.services.ExpenseService;
import pl.nqriver.homebudget.services.dtos.ExpenseDto;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseDto> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpense(@PathVariable Long id) {
        ExpenseDto expenseResponse = expenseService.getExpense(id);
        return ResponseEntity.ok().body(expenseResponse);
    }

    @GetMapping("/filter")
    public List<ExpenseDto> getAllExpensesBetweenDates(@RequestParam("from") String fromDate,
                                                       @RequestParam("to") String toDate) {
        return expenseService.findAllBetweenDates(fromDate, toDate);
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> setExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto expenseResponse = expenseService.setExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseResponse);
    }

    @PutMapping
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        return ResponseEntity.ok().body(expenseService.updateExpense(id, expenseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExpense(@PathVariable Long id, Authentication authentication) {
        expenseService.deleteExpense(id, authentication);
        return ResponseEntity.noContent().build();
    }

}
