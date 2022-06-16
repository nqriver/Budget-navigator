package pl.nqriver.homebudget.services.dtos.csv;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.AssetCategory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class AssetCsvRecord implements CsvRecord {
    private Long id;
    private BigDecimal amount;
    private Instant incomeDate;
    private AssetCategory category;

    @Override
    public List<String> getPropertiesAsList() {
        return List.of(
                id.toString(),
                amount.toString(),
                incomeDate.toString(),
                category.toString()
        );
    }

    @Override
    public List<String> getHeader() {
        return List.of(
                "ID",
                "AMOUNT",
                "INCOME DATE",
                "CATEGORY"
        );
    }
}
