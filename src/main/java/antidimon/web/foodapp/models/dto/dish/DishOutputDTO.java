package antidimon.web.foodapp.models.dto.dish;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishOutputDTO {

    private long id;
    private String name;
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal carbs;

}
