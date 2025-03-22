package antidimon.web.foodapp.models.dto.stat;


import antidimon.web.foodapp.models.entities.StatStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodStatInputDTO {

    @NotNull
    private long userId;
    @NotBlank
    private String dishName;
    @NotNull
    private StatStatus status;
    @NotNull
    private BigDecimal grams;

}
