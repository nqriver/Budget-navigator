package pl.nqriver.homebudget.validators;

import pl.nqriver.homebudget.enums.AssetCategory;
import pl.nqriver.homebudget.validators.annotiations.ExistingAssetCategory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.EnumSet;

public class AssetCategoryValidator implements ConstraintValidator<ExistingAssetCategory, AssetCategory> {
    @Override
    public boolean isValid(AssetCategory assetCategory, ConstraintValidatorContext constraintValidatorContext) {
        return EnumSet.allOf(AssetCategory.class).contains(assetCategory);
    }
}
