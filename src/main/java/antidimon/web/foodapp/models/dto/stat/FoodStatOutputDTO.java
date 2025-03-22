package antidimon.web.foodapp.models.dto.stat;


import antidimon.web.foodapp.models.entities.StatStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FoodStatOutputDTO {

    private long userId;
    private String dishName;
    private StatStatus status;
    private BigDecimal grams;
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal carbs;
    private LocalDateTime date;

}
