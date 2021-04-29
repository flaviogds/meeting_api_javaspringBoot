package room.meeting.managerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import room.meeting.managerapi.entity.Room;
import room.meeting.managerapi.dto.request.RoomDTO;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "startHour",  source = "startHour",   dateFormat = "HH:mm:ss")
    @Mapping(target = "endHour",    source = "endHour",     dateFormat = "HH:mm:ss")
    Room toModel(RoomDTO roomDTO);

    RoomDTO toDTO(Room room);
}