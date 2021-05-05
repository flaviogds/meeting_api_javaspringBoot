package room.meeting.managerapi.controller;

import java.util.List;

import room.meeting.managerapi.dto.request.MeetingDTO;
import room.meeting.managerapi.service.MeetingService;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.exception.MeetingNotFoundException;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/meeting")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createMeeting(@RequestBody @Valid MeetingDTO meetingDTO){
        return meetingService.createMeeting(meetingDTO);
    }

    @GetMapping
    public List<MeetingDTO> listAll(){
        return meetingService.listAll();
    }

    @GetMapping("/findByDate")
    public List<MeetingDTO> listByDate(
            @RequestParam("start") String start,
            @RequestParam(value = "end", required = false) String end,
            @RequestParam("list") String list){
        return  meetingService.listByDate(start, end, list);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(
            @PathVariable Long id,
            @RequestBody @Valid MeetingDTO meetingDTO) throws MeetingNotFoundException {
        return meetingService.updateById(id, meetingDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws MeetingNotFoundException {
        meetingService.deleteById(id);
    }
}