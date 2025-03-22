package antidimon.web.foodapp.models.dto.stat;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DayTotalFoodStats {

    private long userId;
    private boolean metTheNorm;
    private BigDecimal totalCalories;
    private BigDecimal totalProtein;
    private BigDecimal totalFat;
    private BigDecimal totalCarbs;
    private List<FoodStatOutputDTOWithoutID> foodStats;

}
