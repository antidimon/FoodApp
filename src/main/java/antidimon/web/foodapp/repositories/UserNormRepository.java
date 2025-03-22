package antidimon.web.foodapp.repositories;

import antidimon.web.foodapp.models.entities.MyUser;
import antidimon.web.foodapp.models.entities.UserNorm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNormRepository extends JpaRepository<UserNorm, Long> {
}
