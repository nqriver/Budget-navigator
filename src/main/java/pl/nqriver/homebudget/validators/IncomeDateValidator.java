package pl.nqriver.homebudget.validators;

import pl.nqriver.homebudget.enums.AssetValidatorEnum;
import pl.nqriver.homebudget.services.dtos.AssetDto;

import java.util.Objects;

class IncomeDateValidator implements Validator {

    @Override
    public ValidatorMessage valid(AssetDto assetDto, ValidatorMessage message) {
        if (Objects.isNull(assetDto.getIncomeDate())) {
            message.setMessage(AssetValidatorEnum.NO_INCOME_DATE.getMessage());
            message.setCode("4368D26C-3E2C-4BFC-8D73-0F611C5C4A58");
        }
        return message;
    }
}
