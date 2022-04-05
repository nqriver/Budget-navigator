package pl.nqriver.homebudget.service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;


@Data
public class AssetDto {
    private UUID id;
    private BigDecimal amount;

}
