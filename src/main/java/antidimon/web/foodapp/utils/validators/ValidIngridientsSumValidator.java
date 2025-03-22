package antidimon.web.foodapp.utils.validators;

import antidimon.web.foodapp.models.dto.dish.DishInputDTO;
import antidimon.web.foodapp.utils.annotations.ValidIngridientsSum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ValidIngridientsSumValidator implements ConstraintValidator<ValidIngridientsSum, DishInputDTO> {
    @Override
    public boolean isValid(DishInputDTO dishInputDTO, ConstraintValidatorContext context) {
        BigDecimal sum = dishInputDTO.getProtein()
                .add(dishInputDTO.getFat())
                .add(dishInputDTO.getCarbs());
        return sum.compareTo(BigDecimal.valueOf(100)) <= 0;
    }
}
