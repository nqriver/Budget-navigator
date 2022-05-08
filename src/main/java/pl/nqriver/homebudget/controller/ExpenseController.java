package pl.nqriver.homebudget.controller;

import org.springframework.web.bind.annotation.*;
import pl.nqriver.homebudget.service.ExpenseService;
import pl.nqriver.homebudget.service.dto.ExpenseDto;

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
