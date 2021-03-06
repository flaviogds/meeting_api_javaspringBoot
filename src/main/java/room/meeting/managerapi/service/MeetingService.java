package room.meeting.managerapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import room.meeting.managerapi.dto.request.MeetingDTO;
import room.meeting.managerapi.entity.Meeting;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.mapper.MeetingMapper;
import room.meeting.managerapi.repository.MeetingRepository;
import room.meeting.managerapi.exception.MeetingNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper = MeetingMapper.INSTANCE;

    public MessageResponseDTO createMeeting(MeetingDTO meetingDTO) {
        Meeting meetingToSave = meetingMapper.toModel(meetingDTO);
        Meeting savedMeeting = meetingRepository.save(meetingToSave);
        return createMessageResponse("Meeting created successfully");
    }

    public List<MeetingDTO> listAll() {
        return list().stream()
                .map(meetingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MeetingDTO> listByDate(String start, String end, String list) {
        return list().stream()
                .filter(meeting -> (
                        filterByDate(meeting.getDate(), start, end)
                        && filterByStatus(meeting.getStatus(), list)))
                .map(meetingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDTO updateById(Long id, MeetingDTO meetingDTO) throws MeetingNotFoundException {
        verifyIfExists(id);

        Meeting meetingToUpdate = meetingMapper.toModel(meetingDTO);
        Meeting savedMeeting = meetingRepository.save(meetingToUpdate);

        return createMessageResponse("Meeting updated successfully");
    }

    public MessageResponseDTO deleteById(Long id) throws MeetingNotFoundException {
        verifyIfExists(id);

        meetingRepository.deleteById(id);
        return createMessageResponse("Successfully deleted meeting");
    }

    private List<Meeting> list() {
        List<Meeting> allMeeting = meetingRepository.findAll();
        return allMeeting;
    }

    private Meeting verifyIfExists(Long id) throws MeetingNotFoundException {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id));
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
