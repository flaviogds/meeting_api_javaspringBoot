package room.meeting.managerapi.repository;

import room.meeting.managerapi.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
