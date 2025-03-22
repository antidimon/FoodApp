package antidimon.web.foodapp.models.dto.user;


import antidimon.web.foodapp.models.entities.TargetStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyUserOutputDTO {

    private long id;
    private String name;
    private String email;
    private short age;
    private BigDecimal weight;
    private BigDecimal height;
    private TargetStatus status;
    private UserNormOutputDTO norm;

}
