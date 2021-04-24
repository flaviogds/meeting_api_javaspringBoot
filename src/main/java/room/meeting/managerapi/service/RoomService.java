package room.meeting.managerapi.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import room.meeting.managerapi.entity.Room;
import room.meeting.managerapi.dto.request.RoomDTO;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.mapper.RoomMapper;
import room.meeting.managerapi.repository.RoomRepository;
import room.meeting.managerapi.exception.RoomNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;

    public MessageResponseDTO createRoom(RoomDTO roomDTO) {
        Room roomToSave = roomMapper.toModel(roomDTO);
        Room savedRoom = roomRepository.save(roomToSave);
        return createMessageResponse(savedRoom.getId(), "Created Room Meeting with ID ");
    }

    public List<RoomDTO> listAll() {
        List<Room> allRoom = roomRepository.findAll();

        return allRoom.stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> listByDate(String date) {
        List<Room> allRoom = roomRepository.findAll();

        return allRoom.stream()
                .filter(meeting -> compare(meeting.getDate(), date))
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO findById(Long id) throws RoomNotFoundException {
        Room room = verifyIfExists(id);

        return roomMapper.toDTO(room);
    }

    public MessageResponseDTO updateById(Long id, RoomDTO roomDTO) throws RoomNotFoundException {
        verifyIfExists(id);

        Room roomToUpdate = roomMapper.toModel(roomDTO);
        Room savedRoom = roomRepository.save(roomToUpdate);

        return createMessageResponse(savedRoom.getId(), "Updated Details of Room Meeting with ID ");
    }

    public void deleteById(Long id) throws RoomNotFoundException {
        verifyIfExists(id);

        roomRepository.deleteById(id);
    }

    private Room verifyIfExists(Long id) throws RoomNotFoundException {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
    }

    private boolean compare(LocalDate date, String dateRef) {
        String dateToString = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return dateRef.equals(dateToString);
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
