package room.meeting.managerapi.repository;

import room.meeting.managerapi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
