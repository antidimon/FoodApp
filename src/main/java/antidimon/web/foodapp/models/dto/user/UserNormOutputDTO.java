package antidimon.web.foodapp.models.dto.user;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserNormOutputDTO {

    private BigDecimal calorieNorm;
    private BigDecimal proteinNorm;
    private BigDecimal fatNorm;
    private BigDecimal carbsNorm;

}
