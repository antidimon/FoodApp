package antidimon.web.foodapp.models.dto.user;


import antidimon.web.foodapp.models.entities.TargetStatus;
import antidimon.web.foodapp.utils.annotations.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyUserInputDTO {

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid email")
    @UniqueEmail(message = "Email in use")
    private String email;

    @NotNull(message = "Age must not be empty")
    @Min(value = 0, message = "Age must be > 0")
    @Max(value = 100, message = "Age must be < 100")
    private short age;

    @NotNull(message = "Weight must not be empty")
    @Min(value = 2, message = "Weight must be > 2")
    @Max(value = 350,  message = "Weight must be < 350")
    private BigDecimal weight;

    @NotNull(message = "Height must not be empty")
    @Min(value = 50, message = "Height must be > 50")
    @Max(value = 250, message = "Height must be < 250")
    private BigDecimal height;

    @NotNull(message = "Status must not be empty")
    private TargetStatus status;
}
