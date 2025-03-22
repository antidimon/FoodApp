package antidimon.web.foodapp.repositories;


import antidimon.web.foodapp.models.entities.FoodStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodStatsRepository extends JpaRepository<FoodStat, Long> {
}
