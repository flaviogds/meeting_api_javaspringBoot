package room.meeting.managerapi.dto.request;

import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDTO {

    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    private String date;

    @NotEmpty
    private String startHour;

    @NotEmpty
    private String endHour;

    @Valid
    @NotEmpty
    private List<GuestDTO> guests;

    @NotEmpty
    private String status;

    @NotEmpty
    private Boolean publicStatus;

    @NotEmpty
    private Long authorId;

    @NotEmpty
    private String authorName;
}
