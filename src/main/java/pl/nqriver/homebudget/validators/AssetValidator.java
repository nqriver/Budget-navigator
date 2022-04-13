package pl.nqriver.homebudget.validators;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.enums.AssetValidatorEnum;
import pl.nqriver.homebudget.exceptions.AssetIncompleteException;
import pl.nqriver.homebudget.service.dto.AssetDto;

import java.util.Objects;

@Component
public class AssetValidator {

    public void validate(AssetDto assetDto) {
        if (Objects.isNull(assetDto.getAmount())) {
            throw new AssetIncompleteException(AssetValidatorEnum.NO_AMOUNT.getMessage(),
                    "FC4B324A-027E-42C7-9FF9-557C6C7A7E8B");
        }


    }
}
