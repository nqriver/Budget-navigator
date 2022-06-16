package pl.nqriver.homebudget.repositories.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Month;
import java.util.Objects;

@Converter
public class CustomMonthConverter implements AttributeConverter<Month, Short> {
    @Override
    public Short convertToDatabaseColumn(Month month) {
        if (Objects.isNull(month)) {
            return null;
        }
        return (short) month.getValue();
    }

    @Override
    public Month convertToEntityAttribute(Short monthAsShort) {
        if (Objects.isNull(monthAsShort)) {
            return null;
        }
        return Month.of(monthAsShort);
    }
}
