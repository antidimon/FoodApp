package antidimon.web.foodapp.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_stats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "id")
    private Dish dish;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private StatStatus status;

    @Column(name = "grams", nullable = false, precision = 8, scale = 2)
    private BigDecimal grams;

    @Column(name = "total_calories", precision = 8, scale = 2)
    private BigDecimal calories;

    @Column(name = "total_protein", precision = 8, scale = 2)
    private BigDecimal protein;

    @Column(name = "total_fat", precision = 8, scale = 2)
    private BigDecimal fat;

    @Column(name = "total_carbs", precision = 8, scale = 2)
    private BigDecimal carbs;

    @Column(name = "created_at")
    @Basic
    @CreationTimestamp
    private LocalDateTime date;

}
