package room.meeting.managerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeetingNotFoundException extends Exception {
    public MeetingNotFoundException(Long id) {
        super("No meeting found with id" + id);
    }
}
