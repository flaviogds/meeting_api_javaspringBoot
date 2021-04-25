package room.meeting.managerapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import room.meeting.managerapi.entity.Room;
import room.meeting.managerapi.dto.request.RoomDTO;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "date",       source = "date",        dateFormat = "dd-MM-yyyy")
    @Mapping(target = "startHour",  source = "startHour",   dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
    @Mapping(target = "endHour",    source = "endHour",     dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
    Room toModel(RoomDTO roomDTO);

    RoomDTO toDTO(Room room);
}