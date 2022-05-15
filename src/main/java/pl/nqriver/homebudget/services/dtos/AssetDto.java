package pl.nqriver.homebudget.services.dtos;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.validators.annotiations.ExistingAssetCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class AssetDto {
    private Long id;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    private Instant incomeDate;

    @ExistingAssetCategory
    private AssetCategory category;
}
