package room.meeting.managerapi.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import room.meeting.managerapi.dto.request.UserLoginDTO;
import room.meeting.managerapi.exception.UserNotFoundException;
import room.meeting.managerapi.mapper.UserLoginMapper;

import java.util.Collections;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService implements UserDetailsService{

    private final UserService userService;
    private final UserLoginMapper userLoginMapper = UserLoginMapper.INSTANCE;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserLoginDTO user  = null;
        try {
            user = userLoginMapper.toDTO(userService.findByUserName(userName));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if(user == null) throw new UsernameNotFoundException(userName);

        return new User(user.getUsername(), user.getAuthentication(), Collections.emptyList());
    }
}