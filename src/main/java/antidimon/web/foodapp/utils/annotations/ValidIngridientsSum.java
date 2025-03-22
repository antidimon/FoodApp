package antidimon.web.foodapp.utils.annotations;

import antidimon.web.foodapp.utils.validators.ValidIngridientsSumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidIngridientsSumValidator.class)
public @interface ValidIngridientsSum {
    String message() default "Protein+fat+carbs <= 100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
