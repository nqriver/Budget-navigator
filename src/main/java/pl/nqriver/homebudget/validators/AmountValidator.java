package pl.nqriver.homebudget.validators;

import pl.nqriver.homebudget.enums.AssetValidatorEnum;
import pl.nqriver.homebudget.service.dto.AssetDto;

import java.util.Objects;

class AmountValidator implements Validator {

    private Validator next = new IncomeDateValidator();

    @Override
    public ValidatorMessage valid(AssetDto assetDto, ValidatorMessage message) {
        if (Objects.isNull(assetDto.getAmount()))  {
            message.setMessage(AssetValidatorEnum.NO_AMOUNT.getMessage());
            message.setCode("BF078C26-9F67-4D97-ABF4-FB7462064A5B");
        }
        return next.valid(assetDto, message);
    }
}
