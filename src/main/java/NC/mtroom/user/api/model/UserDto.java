package NC.mtroom.user.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
public class UserDto {

    private String login;
    private String username;
    private String password;

}
