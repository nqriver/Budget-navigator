package pl.nqriver.homebudget.validators;

import pl.nqriver.homebudget.services.dtos.AssetDto;

public interface Validator {

    ValidatorMessage valid(AssetDto assetDto, ValidatorMessage message);
}
