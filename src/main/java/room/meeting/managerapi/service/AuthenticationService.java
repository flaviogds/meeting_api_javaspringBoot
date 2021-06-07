package room.meeting.managerapi.service;

import static java.util.Collections.emptyList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import room.meeting.managerapi.entity.UserLogin;
import room.meeting.managerapi.mapper.LoginMapper;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService implements UserDetailsService{

    private final UserService userService;
    private final LoginMapper loginMapper = LoginMapper.INSTANCE;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserLogin user = loginMapper.toModel(userService.findByUsernameOrEmail(userName, null));

        if(user == null) throw new UsernameNotFoundException(userName);

        return new User(user.getUsername(), user.getAuthentication(), emptyList());
    }
}