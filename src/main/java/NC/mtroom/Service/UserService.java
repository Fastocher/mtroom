package NC.mtroom.Service;

import NC.mtroom.Entity.DaoUser;
import NC.mtroom.Exception.UserAlreadyExist;
import NC.mtroom.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    public UserModel registration (DaoUser user) throws UserAlreadyExist {
//        if (userRepository.findByLogin(user.getLogin()) != null ){
//            throw new UserAlreadyExist("Пользователь уже существует");
//        }
//        userRepository.save(user);
//        return UserModel.toModel(user);
//    }
}
