package NC.mtroom.user.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    private String login;
    private String name;
    private String token;


}
