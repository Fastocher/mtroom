package NC.mtroom.Model;

import NC.mtroom.Entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    private String login;
    private String username;

    public static UserModel toModel(UserEntity entity){
        UserModel model = new UserModel();
        model.setLogin(entity.getLogin());
        model.setUsername(entity.getUsername());
        return model;
    }
}
