package room.meeting.managerapi.dto.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MessageResponseDTO {
    private String message;
}
