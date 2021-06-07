package room.meeting.managerapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import room.meeting.managerapi.security.SecurityConstants;

@RestController
@RequestMapping(SecurityConstants.API_URL)
public class ApplicationController {

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String viewStatus(){
        return "Cool, the server is ready to you.";
    }
}
