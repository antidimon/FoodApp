package antidimon.web.foodapp.models.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "user_norms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserNorm {

    @Id
    private long userId;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "id")
    private MyUser user;

    @Column(name = "calorie_norm", precision = 8, scale = 2)
    private BigDecimal calorieNorm;

    @Column(name = "protein_norm", precision = 8, scale = 2)
    private BigDecimal proteinNorm;

    @Column(name = "fat_norm", precision = 8, scale = 2)
    private BigDecimal fatNorm;

    @Column(name = "carbs_norm", precision = 8, scale = 2)
    private BigDecimal carbsNorm;

}
