package room.meeting.managerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import room.meeting.managerapi.dto.request.MeetingDTO;
import room.meeting.managerapi.entity.Meeting;

@Mapper
public interface MeetingMapper {
    MeetingMapper INSTANCE = Mappers.getMapper(MeetingMapper.class);

    @Mapping(target = "startHour",  source = "startHour",   dateFormat = "HH:mm:ss")
    @Mapping(target = "endHour",    source = "endHour",     dateFormat = "HH:mm:ss")
    Meeting toModel(MeetingDTO meetingDTO);

    MeetingDTO toDTO(Meeting meeting);
}