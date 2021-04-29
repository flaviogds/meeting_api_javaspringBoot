package room.meeting.managerapi.controller;

import java.util.List;

import room.meeting.managerapi.service.RoomService;
import room.meeting.managerapi.dto.request.RoomDTO;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.exception.RoomNotFoundException;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/room")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createMeeting(@RequestBody @Valid RoomDTO roomDTO){
        return roomService.createRoom(roomDTO);
    }

    @GetMapping 
    public List<RoomDTO> listAll(){
        return roomService.listAll();
    }

    @GetMapping("/?date={date}")
    public List<RoomDTO> listByDate(@PathVariable String date){
        return  roomService.listByDate(date);
    }

    @GetMapping("/{id}")
    public RoomDTO findById(@PathVariable Long id) throws RoomNotFoundException {
        return roomService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO) throws RoomNotFoundException {
        return roomService.updateById(id, roomDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws RoomNotFoundException {
        roomService.deleteById(id);
    }
}
