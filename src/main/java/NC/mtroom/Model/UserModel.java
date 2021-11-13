package NC.mtroom.Model;

import NC.mtroom.Entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    private String roomID;
    private String username;

    public static UserModel toModel(UserEntity userEntity){
        UserModel model = new UserModel();
      //  model.setLogin(userEntity.getUsername);
        model.setUsername(userEntity.getUsername());
        return model;
    }
}
