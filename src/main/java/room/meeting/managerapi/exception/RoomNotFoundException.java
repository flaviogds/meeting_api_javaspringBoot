package room.meeting.managerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(Long id) {
        super("No Room with ID " + id);
    }
}
