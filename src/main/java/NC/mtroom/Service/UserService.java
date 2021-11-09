package NC.mtroom.Service;

import NC.mtroom.Entity.UserEntity;
import NC.mtroom.Model.UserModel;
import NC.mtroom.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public UserEntity getuser (String login) throws Exception {
        UserEntity userEntity = userRepository.findByLogin(login);
        if (userRepository.findByLogin(login) == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            throw new Exception("Пользователь с логином "+ login +" не найден ");
        }
        return userEntity;
    }
}
