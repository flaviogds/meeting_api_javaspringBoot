package room.meeting.managerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import room.meeting.managerapi.dto.request.UserProfileDTO;
import room.meeting.managerapi.entity.UserProfile;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserProfile toModel(UserProfileDTO userProfileDTO);

    UserProfileDTO toDTO(UserProfile userProfile);
}