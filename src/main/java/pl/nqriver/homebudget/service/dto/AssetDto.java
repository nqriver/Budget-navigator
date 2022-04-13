package pl.nqriver.homebudget.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;


@Builder
@Getter
@EqualsAndHashCode
public class AssetDto {
    private Long id;
    private BigDecimal amount;
    private Instant incomeDate;
}
