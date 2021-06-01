package room.meeting.managerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception  {
    public UserNotFoundException(String param, String value) {
        super("No user found with " + param + " " + value);
    }
}