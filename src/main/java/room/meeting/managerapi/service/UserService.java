package room.meeting.managerapi.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import room.meeting.managerapi.dto.request.MeetingDTO;
import room.meeting.managerapi.entity.Meeting;
import room.meeting.managerapi.entity.UserProfile;
import room.meeting.managerapi.dto.request.UserProfileDTO;
import room.meeting.managerapi.dto.response.MessageResponseDTO;
import room.meeting.managerapi.exception.MeetingNotFoundException;
import room.meeting.managerapi.exception.UserNotFoundException;
import room.meeting.managerapi.mapper.MeetingMapper;
import room.meeting.managerapi.mapper.UserMapper;
import room.meeting.managerapi.repository.UserRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final MeetingMapper meetingMapper = MeetingMapper.INSTANCE;

    public List<UserProfileDTO> listAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserProfileDTO findByUserId(Long id) throws UserNotFoundException {
        UserProfileDTO userProfileDTO = userMapper.toDTO(verifyIfExists(id, null, null));
        return userProfileDTO;
    }

    public UserProfileDTO findByUserName(String userName) throws UserNotFoundException {
        UserProfileDTO userProfileDTO = userMapper.toDTO(verifyIfExists(null, userName, null));
        return userProfileDTO;
    }

    public UserProfileDTO findByEmail(String email) throws UserNotFoundException {
        UserProfileDTO userProfileDTO = userMapper.toDTO(verifyIfExists(null, null, email));
        return userProfileDTO;
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

    @SneakyThrows
    public UserProfile addMeeting(Long userId, Meeting meeting) {
        UserProfile userProfile = verifyIfExists(userId, null, null);
        userProfile.getMeeting().add(meeting);
        return userRepository.save(userProfile);
    }

    @SneakyThrows
    public UserProfile updateMeeting(Long userId, Meeting oldMeeting, Meeting meetingToSave) {
        UserProfile userProfile = verifyIfExists(userId, null, null);
        userProfile.getMeeting()
                .set(userProfile.getMeeting().indexOf(oldMeeting), meetingToSave);
        return userRepository.save((userProfile));
    }

    @SneakyThrows
    public UserProfile deleteMeeting(Long userId, Meeting meetingToDelete) {
        UserProfile userProfile = verifyIfExists(userId, null, null);
        userProfile.getMeeting().remove(meetingToDelete);
        return userRepository.save((userProfile));
    }

    private UserProfile verifyIfExists(
                @Nullable Long id,
                @Nullable String userName,
                @Nullable String email) throws UserNotFoundException {
        if(id != null){
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("id", id.toString()));
        }
        else if(userName != null){
            return userRepository
                        .findAll()
                        .stream()
                        .filter(user -> user.getUserName().equals(userName))
                        .findFirst()
                    .orElseThrow(() -> new UserNotFoundException("username", userName));
        }
        else if(email != null){
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
