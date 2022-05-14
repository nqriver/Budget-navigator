package pl.nqriver.homebudget.controllers;

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

    @GetMapping("/filter")
    public List<ExpenseDto> getAllExpensesBetweenDates(@RequestParam("from") String fromDate,
                                                       @RequestParam("to") String toDate) {
        return expenseService.findAllBetweenDates(fromDate, toDate);
    }

    @PostMapping
    public ExpenseDto setExpense(@RequestBody ExpenseDto expenseDto) {
        return expenseService.setExpense(expenseDto);
    }

    @PutMapping
    public ExpenseDto updateExpense(@RequestBody ExpenseDto expenseDto) {
        return expenseService.updateExpense(expenseDto);
    }

    @DeleteMapping
    public void deleteExpense(@RequestBody ExpenseDto expenseDto) {
        expenseService.deleteExpense(expenseDto);
    }

}
