package room.meeting.managerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import room.meeting.managerapi.dto.request.UserLoginDTO;
import room.meeting.managerapi.dto.request.UserProfileDTO;

@Mapper
public interface UserLoginMapper {
    UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

    UserLoginDTO toDTO(UserProfileDTO userProfileDTO);
}
