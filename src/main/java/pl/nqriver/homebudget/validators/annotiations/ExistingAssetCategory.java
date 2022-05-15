package pl.nqriver.homebudget.validators.annotiations;


import pl.nqriver.homebudget.validators.AssetCategoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = AssetCategoryValidator.class)
@Documented
public @interface ExistingAssetCategory {
    String message() default "Invalid value for category";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
