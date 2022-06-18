package pl.nqriver.homebudget.services.dtos.csv;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.AssetCategory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Data
public class AssetCsvRecord implements CsvRecord {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Instant incomeDate;
    @NotNull
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
