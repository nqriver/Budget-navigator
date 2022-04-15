package pl.nqriver.homebudget.validators;

import pl.nqriver.homebudget.service.dto.AssetDto;

public interface Validator {

    ValidatorMessage valid(AssetDto assetDto, ValidatorMessage message);
}
