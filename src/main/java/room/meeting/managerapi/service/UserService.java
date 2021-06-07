package room.meeting.managerapi.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import room.meeting.managerapi.entity.UserProfile;
import room.meeting.managerapi.mapper.UserMapper;
import room.meeting.managerapi.repository.UserRepository;
import room.meeting.managerapi.dto.request.UserProfileDTO;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.exception.UserNotFoundException;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @SneakyThrows
    public Boolean validateUsernameOrEmail(String username, String email) {
        UserProfile userProfile = verifyIfExists(null, username, email);
        return  userProfile == null;
    }

    public UserProfileDTO findByUserId(Long id) throws UserNotFoundException {
        UserProfileDTO userProfileDTO = userMapper.toDTO(verifyIfExists(id, null, null));
        return userProfileDTO;
    }

    @SneakyThrows
    public UserProfileDTO findByUsernameOrEmail(String userName, String email) {
        if(!userName.isEmpty()){
            return userMapper.toDTO(verifyIfExists(null, userName, null));
        }
        else {
            return userMapper.toDTO(verifyIfExists(null, null, email));
        }
    }

    public MessageResponseDTO createUser(UserProfileDTO userProfileDTO) {
        UserProfile userToSave = userMapper.toModel(userProfileDTO);
        userRepository.save(userToSave);
        return createMessageResponse("User created successfully");
    }

    public MessageResponseDTO updateById(Long id, UserProfileDTO userProfileDTO) throws UserNotFoundException {
        verifyIfExists(id, null, null);
        UserProfile userToUpdate = userMapper.toModel(userProfileDTO);
        userRepository.save(userToUpdate);
        return createMessageResponse("User profile updated successfully");
    }

    public MessageResponseDTO deleteById(Long id) throws UserNotFoundException {
        verifyIfExists(id, null, null);
        userRepository.deleteById(id);
        return createMessageResponse("Successfully deleted user account");
    }

    private List<UserProfile> listAllUsers() {
        return userRepository.findAll();
    }

    private UserProfile verifyIfExists(
                @Nullable Long id,
                @Nullable String username,
                @Nullable String email) throws UserNotFoundException {
        if(id != null){
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        }
        else if(username != null && !username.isEmpty()){
            return userRepository
                        .findAll()
                        .stream()
                        .filter(user -> user.getUsername().equals(username))
                        .findFirst()
                    .orElseThrow(() -> new UserNotFoundException("username", username));
        }
        else if(email != null && !email.isEmpty()){
            return userRepository
                    .findAll()
                    .stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new UserNotFoundException("email", email));
        }
        return null;
    }

    private MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }
}
