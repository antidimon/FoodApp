package antidimon.web.foodapp.mappers;

import antidimon.web.foodapp.models.dto.dish.DishInputDTO;
import antidimon.web.foodapp.models.dto.dish.DishOutputDTO;
import antidimon.web.foodapp.models.entities.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "statsWithDish", ignore = true)
    @Mapping(target = "id", ignore = true)
    Dish toEntity(DishInputDTO dishInputDTO);

    DishOutputDTO toOutputDTO(Dish dish);
}
