package room.meeting.managerapi.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {

    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Email
    private String email;
}