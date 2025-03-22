package antidimon.web.foodapp.utils.validators;

import antidimon.web.foodapp.repositories.MyUserRepository;
import antidimon.web.foodapp.utils.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private MyUserRepository myUserRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return myUserRepository.findByEmail(email).isEmpty();
    }
}
