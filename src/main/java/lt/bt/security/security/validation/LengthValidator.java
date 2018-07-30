package lt.bt.security.security.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LengthValidator implements ConstraintValidator<Length, String> {
    @Override
    public void initialize(Length constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() < 6) {
            return false;
        }
        return true;
    }
}
