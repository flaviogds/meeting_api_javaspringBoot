package room.meeting.managerapi.service;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import room.meeting.managerapi.entity.Meeting;
import room.meeting.managerapi.dto.request.MeetingDTO;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.mapper.MeetingMapper;
import room.meeting.managerapi.exception.UserNotFoundException;
import room.meeting.managerapi.exception.MeetingNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingService {
    private final UserService userService;
    private final MeetingMapper meetingMapper = MeetingMapper.INSTANCE;

    public List<MeetingDTO> listAllPublicMeeting() {
        return null;
    }

    public List<MeetingDTO> listAll(Long UserId) {
        return list(UserId);
    }

    public List<MeetingDTO> listByDate(Long UserId, String start, String end, String list) {
        return list(UserId).stream()
                .filter(meeting -> (
                        filterByDate(meeting.getDate(), start, end)
                        && filterByStatus(meeting.getStatus(), list)))
                .collect(Collectors.toList());
    }

    public MessageResponseDTO createMeeting(Long userId, MeetingDTO meetingDTO) {
        Meeting meeting = meetingMapper.toModel((meetingDTO));
        userService.addMeeting(userId, meeting);

        return createMessageResponse("Meeting created successfully");
    }

    public MessageResponseDTO updateById(Long userId, Long id, MeetingDTO meetingDTO) throws MeetingNotFoundException {
        Meeting meetingToUpdate = meetingMapper.toModel((meetingDTO));
        Meeting oldMeeting = meetingMapper.toModel(verifyIfExists(userId, id));
        userService.updateMeeting(userId, oldMeeting, meetingToUpdate);
        return createMessageResponse("Meeting updated successfully");
    }

    public MessageResponseDTO deleteById(Long userId, Long id) throws MeetingNotFoundException {
        Meeting meetingToDelete = meetingMapper.toModel(verifyIfExists(userId, id));
        userService.deleteMeeting(userId, meetingToDelete);
        return createMessageResponse("Successfully deleted meeting");
    }

    private MeetingDTO verifyIfExists(Long userId, Long id) throws MeetingNotFoundException {
        return list(userId)
                .stream()
                .filter(mee -> mee.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new MeetingNotFoundException(id));
    }

    private List<MeetingDTO> list(Long UserId) {
        try {
            return userService.findByUserId(UserId).getMeeting();
        } catch (UserNotFoundException err) {
            err.printStackTrace();
            return null;
        }
    }

    private boolean filterByStatus(String status, String list){
        if(list.equals("all")) return true;
        else return status.equals(list);
    }

    private boolean filterByDate(String meetingDate, String start, String end) {
        boolean valid = false;
        LocalDate date = LocalDate.parse(meetingDate);
        LocalDate startDate = LocalDate.parse(start);

        if(end != null && !end.isEmpty()){
            LocalDate endDate = LocalDate.parse(end);

            if((date.isEqual(startDate) || date.isAfter(startDate))
                    && (date.isEqual(endDate) || date.isBefore(endDate))){
                valid = true;
            }
            else valid = false;
        }
        else{
            if(date.isEqual(startDate)) valid = true;
        }
        return valid;
    }

    private MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }
}
