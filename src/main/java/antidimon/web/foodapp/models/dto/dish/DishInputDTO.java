package antidimon.web.foodapp.models.dto.dish;


import antidimon.web.foodapp.utils.annotations.ValidIngridientsSum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ValidIngridientsSum
public class DishInputDTO {

    @NotBlank
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 862)
    private BigDecimal calories;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private BigDecimal protein;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private BigDecimal fat;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private BigDecimal carbs;


}
