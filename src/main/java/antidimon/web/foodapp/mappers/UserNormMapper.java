package antidimon.web.foodapp.mappers;

import antidimon.web.foodapp.models.dto.user.UserNormOutputDTO;
import antidimon.web.foodapp.models.entities.UserNorm;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserNormMapper {

    UserNormOutputDTO toOutputDTO(UserNorm norm);


}
