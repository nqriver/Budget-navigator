package pl.nqriver.homebudget.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Builder
@Getter
public class AssetDto {
    private Long id;
    private BigDecimal amount;
}
