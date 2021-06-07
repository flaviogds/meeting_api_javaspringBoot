package room.meeting.managerapi.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotEmpty
    @Size(max = 100)
    private String username;

    @NotEmpty
    @Size(max = 100)
    private String authentication;
}
