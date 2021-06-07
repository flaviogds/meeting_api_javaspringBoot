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
import room.meeting.managerapi.exception.MeetingNotFoundException;
import room.meeting.managerapi.repository.MeetingRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper = MeetingMapper.INSTANCE;

    public List<MeetingDTO> listAllPublicMeeting() {
        return list()
                .stream()
                .filter(Meeting::getPublicStatus)
                .map(meetingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MeetingDTO> listAll(Long userId) {
        return list()
                .stream()
                .filter(meeting -> meeting.getAuthorId().equals(userId))
                .map(meetingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MeetingDTO> listByDate(Long userId, String start, String end, String list) {
        return list()
                .stream()
                .filter(meeting -> (
                        meeting.getAuthorId().equals(userId)
                        && filterByDate(meeting.getDate(), start, end)
                        && filterByStatus(meeting.getStatus(), list)))
                .map(meetingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDTO createMeeting(MeetingDTO meetingDTO) {
        Meeting meeting = meetingMapper.toModel((meetingDTO));
        meetingRepository.save(meeting);
        return createMessageResponse("Meeting created successfully");
    }

    public MessageResponseDTO updateById(Long userId, Long id, MeetingDTO meetingDTO) throws MeetingNotFoundException {
        Meeting meetingToUpdate = meetingMapper.toModel((meetingDTO));
        if(userId.equals(meetingToUpdate.getAuthorId())){
            verifyIfExists(id);
            meetingRepository.save(meetingToUpdate);
            return createMessageResponse("Meeting updated successfully");
        }
        else return createMessageResponse("Permission denied to update meeting");
    }

    public MessageResponseDTO deleteById(Long userId, Long id) throws MeetingNotFoundException {
        Meeting meetingToDelete = verifyIfExists(id);

        if(userId.equals(meetingToDelete.getAuthorId())){
            meetingRepository.deleteById(id);
            return createMessageResponse("Successfully deleted meeting");
        }
        else return createMessageResponse("Permission denied to delete meeting");
    }

    private Meeting verifyIfExists(Long id) throws MeetingNotFoundException {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));
    }

    private List<Meeting> list() {
        return meetingRepository.findAll();
    }

    private boolean filterByStatus(String status, String list){
        if(list.equals("all")) return true;
        else return status.equals(list);
    }

    private boolean filterByDate(LocalDate date, String start, String end) {
        boolean valid = false;
        LocalDate startDate = LocalDate.parse(start);

        if(end != null && !end.isEmpty()){
            LocalDate endDate = LocalDate.parse(end);
            valid = (date.isEqual(startDate) || date.isAfter(startDate))
                    && (date.isEqual(endDate) || date.isBefore(endDate));
        }
        else valid = date.isEqual(startDate);

        return valid;
    }

    private MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }
}
