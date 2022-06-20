package pl.nqriver.homebudget.mappers;

import org.mapstruct.Mapper;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;
import pl.nqriver.homebudget.services.dtos.csv.RecurringExpenseCsvRecord;

@Mapper(componentModel = "spring")
public interface RecurringExpenseToCsvMapper {

    RecurringExpenseCsvRecord responseToCsvRecord(RecurringExpenseResponse response);

}
