package NC.mtroom.user.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.MessageInterpolator;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Pattern(regexp = "[a-z0-9A-Z_]+",message = "Not available characters for login! Only a-z, 0-9, '_' available")
    private String login;

    @Pattern(regexp = "[a-z0-9A-Z_]+",message = "Not available characters for username! Only a-z, 0-9, '_' available")
    private String username;

    @NotBlank
    @Size(min = 5,max = 15,message = "Password must be between 5 and 15 characters long")
    @Pattern(regexp = "[^ ]+",message = "Password can't include space")
    private String password;

}
