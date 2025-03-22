package antidimon.web.foodapp.models.dto.user;


import antidimon.web.foodapp.models.entities.TargetStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyUserEditDTO {

    private String name;
    private String email;
    private Short age;
    private BigDecimal weight;
    private BigDecimal height;
    private TargetStatus status;

}
