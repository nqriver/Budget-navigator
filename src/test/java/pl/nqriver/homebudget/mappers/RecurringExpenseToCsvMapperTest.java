package pl.nqriver.homebudget.mappers;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.nqriver.homebudget.services.dtos.RecurringExpenseResponse;
import pl.nqriver.homebudget.services.dtos.csv.RecurringExpenseCsvRecord;

import java.math.BigDecimal;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecurringExpenseToCsvMapperTest {

    private RecurringExpenseToCsvMapper mapper = Mappers.getMapper(RecurringExpenseToCsvMapper.class);

    @Test
    void shouldMapResponseCorrectly() {
        RecurringExpenseResponse response = RecurringExpenseResponse.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(212))
                .day((short) 12)
                .month((short) 2)
                .build();

        RecurringExpenseCsvRecord csvRecord = mapper.responseToCsvRecord(response);

        assertThat(csvRecord.getId()).isEqualTo(response.getId());
        assertThat(csvRecord.getAmount()).isEqualTo(response.getAmount());
        assertThat(csvRecord.getDay()).isEqualTo(response.getDay());
        assertThat(csvRecord.getMonth()).isEqualTo(response.getMonth());
    }




}