package pl.nqriver.homebudget.validators;

import pl.nqriver.homebudget.enums.ExpenseCategory;
import pl.nqriver.homebudget.validators.annotiations.ExistingExpenseCategory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;

public class ExpenseCategoryValidator implements ConstraintValidator<ExistingExpenseCategory, ExpenseCategory> {
    @Override
    public boolean isValid(ExpenseCategory expenseCategory, ConstraintValidatorContext constraintValidatorContext) {
        return EnumSet.allOf(ExpenseCategory.class).contains(expenseCategory);
    }
}
