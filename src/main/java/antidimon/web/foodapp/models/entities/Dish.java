package antidimon.web.foodapp.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "dishes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "calories", precision = 8, scale = 2)
    private BigDecimal calories;

    @Column(name = "protein", precision = 8, scale = 2)
    private BigDecimal protein;

    @Column(name = "fat", precision = 8, scale = 2)
    private BigDecimal fat;

    @Column(name = "carbs", precision = 8, scale = 2)
    private BigDecimal carbs;

    @OneToMany(mappedBy = "dish")
    private List<FoodStat> statsWithDish;

}
