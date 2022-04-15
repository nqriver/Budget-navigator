package pl.nqriver.homebudget.validators;

import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.exceptions.AssetIncompleteException;
import pl.nqriver.homebudget.service.dto.AssetDto;

@Component
public class AssetValidator {

    private final Validator validator = new AmountValidator();

    public void validate(AssetDto assetDto) {
        ValidatorMessage validatorMessage = validator.valid(assetDto, new ValidatorMessage());

        if (!validatorMessage.getMessage().isEmpty()) {
            throw new AssetIncompleteException(validatorMessage.getMessage(), validatorMessage.getCode());
        }

    }
}
