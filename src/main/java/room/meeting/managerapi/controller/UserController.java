package room.meeting.managerapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @GetMapping
    @CrossOrigin(origins = "*")
    public String getByUserName(){
        return "getByName";
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public String getUserByEmail(){
        return "getByEmail";
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public String createUser(){
        return "createNewUser";
    }

    @PutMapping
    @CrossOrigin(origins = "*")
    public String updateUserProfile(){
        return "updateUserProfile";
    }

    @DeleteMapping
    @CrossOrigin(origins = "*")
    public String deleteAccount(){
        return "deleteUserAccount";
    }

}
