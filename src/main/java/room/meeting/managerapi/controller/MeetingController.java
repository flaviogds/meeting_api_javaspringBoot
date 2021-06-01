package room.meeting.managerapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import room.meeting.managerapi.dto.request.MeetingDTO;
import room.meeting.managerapi.exception.UserNotFoundException;
import room.meeting.managerapi.service.MeetingService;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.exception.MeetingNotFoundException;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v2/meeting")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping
    @CrossOrigin(origins = "https://flaviogds.github.io")
    @ResponseStatus(HttpStatus.OK)
    public List<MeetingDTO> listAllPublicMeeting(){
        return meetingService.listAllPublicMeeting();
    }

    @GetMapping("/{user}")
    @CrossOrigin(origins = "https://flaviogds.github.io")
    @ResponseStatus(HttpStatus.OK)
    public List<MeetingDTO> listAll(@PathVariable Long user){
        return meetingService.listAll(user);
    }

    @GetMapping("/{user}/findByDate")
    @CrossOrigin(origins = "https://flaviogds.github.io")
    @ResponseStatus(HttpStatus.OK)
    public List<MeetingDTO> listByDate(
            @PathVariable Long user,
            @RequestParam("start") String start,
            @RequestParam(value = "end", required = false) String end,
            @RequestParam("list") String list){
        return  meetingService.listByDate(user, start, end, list);
    }

    @PostMapping("/{user}/create")
    @CrossOrigin(origins = "https://flaviogds.github.io")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createMeeting(
            @PathVariable Long user,
            @RequestBody @Valid MeetingDTO meetingDTO) {
        return meetingService.createMeeting(user, meetingDTO);
    }

    @PutMapping("/{user}/{id}")
    @CrossOrigin(origins = "https://flaviogds.github.io")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO updateById(
            @PathVariable Long user,
            @PathVariable Long id,
            @RequestBody @Valid MeetingDTO meetingDTO) throws MeetingNotFoundException {
        return meetingService.updateById(user, id, meetingDTO);
    }

    @DeleteMapping("/{user}/{id}")
    @CrossOrigin(origins = "https://flaviogds.github.io")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO deleteById(
                @PathVariable Long user,
                @PathVariable Long id) throws MeetingNotFoundException {
        return meetingService.deleteById(user, id);
    }
}
