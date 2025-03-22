package antidimon.web.foodapp.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "age", nullable = false)
    private short age;

    @Column(name = "weight", precision = 5, scale = 2, nullable = false)
    private BigDecimal weight;

    @Column(name = "height", precision = 5, scale = 2, nullable = false)
    private BigDecimal height;

    @Column(name = "status", length = 15)
    @Enumerated(EnumType.STRING)
    private TargetStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodStat> stats;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserNorm userNorm;

}
