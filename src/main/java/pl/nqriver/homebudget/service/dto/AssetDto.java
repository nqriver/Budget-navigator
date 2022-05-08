package pl.nqriver.homebudget.service.dto;

import lombok.Builder;
import lombok.Data;
import pl.nqriver.homebudget.enums.AssetCategory;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class AssetDto {
    private Long id;
    private BigDecimal amount;
    private Instant incomeDate;
    private AssetCategory category;
}
