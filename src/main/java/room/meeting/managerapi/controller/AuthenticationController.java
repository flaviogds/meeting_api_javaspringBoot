package room.meeting.managerapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/login")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    @GetMapping
    @CrossOrigin(origins = "*")
    public String authUser(@RequestBody String data){ return "hash";}
}
