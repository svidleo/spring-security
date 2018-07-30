package lt.bt.security.security.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LengthValidator.class)
public @interface Length {
    String message() default "To short!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
