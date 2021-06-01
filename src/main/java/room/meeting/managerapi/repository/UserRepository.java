package room.meeting.managerapi.repository;

import room.meeting.managerapi.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserProfile, Long> {
}
