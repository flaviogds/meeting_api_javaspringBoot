package room.meeting.managerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import room.meeting.managerapi.dto.request.UserProfileDTO;
import room.meeting.managerapi.entity.UserLogin;

@Mapper
public interface LoginMapper {
    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "authentication", source = "authentication")
    UserLogin toModel(UserProfileDTO userProfileDTO);
}