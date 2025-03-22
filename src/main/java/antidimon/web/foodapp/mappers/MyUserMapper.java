package antidimon.web.foodapp.mappers;

import antidimon.web.foodapp.models.dto.user.MyUserInputDTO;
import antidimon.web.foodapp.models.dto.user.MyUserOutputDTO;
import antidimon.web.foodapp.models.entities.MyUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = UserNormMapper.class)
public interface MyUserMapper {

    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "userNorm", ignore = true)
    @Mapping(target = "id", ignore = true)
    MyUser toEntity(MyUserInputDTO myUserInputDTO);

    @Mapping(target = "norm", source = "userNorm")
    MyUserOutputDTO toOutputDTO(MyUser myUser);
}
