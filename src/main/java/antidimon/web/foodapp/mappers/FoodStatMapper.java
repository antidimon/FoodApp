package antidimon.web.foodapp.mappers;


import antidimon.web.foodapp.models.dto.stat.FoodStatOutputDTO;
import antidimon.web.foodapp.models.dto.stat.FoodStatOutputDTOWithoutID;
import antidimon.web.foodapp.models.entities.FoodStat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface FoodStatMapper {

    @Mapping(source = "dish.name", target = "dishName")
    @Mapping(source = "user.id", target = "userId")
    FoodStatOutputDTO toOutputDTO(FoodStat foodStat);

    List<FoodStatOutputDTO> foodStatsToStatOutputDTOs(List<FoodStat> foodStats);

    FoodStatOutputDTOWithoutID toOutputDTOWithoutId(FoodStatOutputDTO foodStat);
}
