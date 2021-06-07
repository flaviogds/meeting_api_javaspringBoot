package room.meeting.managerapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import room.meeting.managerapi.dto.request.UserProfileDTO;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.exception.UserNotFoundException;
import room.meeting.managerapi.security.SecurityConstants;
import room.meeting.managerapi.service.UserService;

@RestController
@RequestMapping(SecurityConstants.API_URL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/validate")
    @CrossOrigin(origins = "*")
    public Boolean validateUsernameOrEmail(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email) {
        return userService.validateUsernameOrEmail(username, email);
    }

    @GetMapping("/user_profile")
    @CrossOrigin(origins = "*")
    public UserProfileDTO findByUsernameOrEmail(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email) {
        return userService.findByUsernameOrEmail(username, email);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "*")
    public MessageResponseDTO createUser(@RequestBody UserProfileDTO userProfileDTO){
        userProfileDTO.setAuthentication(bCryptPasswordEncoder.encode(userProfileDTO.getAuthentication()));
        return userService.createUser(userProfileDTO);
    }

    @PutMapping("/{user}")
    @CrossOrigin(origins = "*")
    public MessageResponseDTO updateUserProfile(@PathVariable Long user, @RequestBody UserProfileDTO userProfileDTO) throws UserNotFoundException {
        return userService.updateById(user, userProfileDTO);
    }

    @DeleteMapping("/{user}")
    @CrossOrigin(origins = "*")
    public MessageResponseDTO deleteAccount(@PathVariable Long user) throws UserNotFoundException {
        return userService.deleteById(user);
    }

}
