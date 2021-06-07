package room.meeting.managerapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import room.meeting.managerapi.entity.UserLogin;
import room.meeting.managerapi.security.SecurityConstants;

@RestController
@RequestMapping(SecurityConstants.API_URL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public void login(@RequestBody UserLogin user){
        user.setAuthentication(bCryptPasswordEncoder.encode(user.getAuthentication()));
    }
}
