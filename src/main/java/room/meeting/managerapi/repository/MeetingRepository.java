package room.meeting.managerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import room.meeting.managerapi.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}