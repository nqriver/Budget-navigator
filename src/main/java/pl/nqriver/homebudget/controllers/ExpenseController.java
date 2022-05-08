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

    @PostMapping
    public void setExpense(@RequestBody ExpenseDto expenseDto) {
        expenseService.setExpense(expenseDto);
    }

}
