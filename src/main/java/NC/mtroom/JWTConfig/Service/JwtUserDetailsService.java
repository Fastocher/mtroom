package NC.mtroom.JWTConfig.Service;

import NC.mtroom.user.api.model.UserDto;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null ) {
            throw new UsernameNotFoundException("Пользователь с именем "+ username +" не найден ");
        }
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(),
                new ArrayList<>());
    }
    //Сохраняет пользователя в таблицу
    public UserEntity save(UserDto user) {
        UserEntity newUser = new UserEntity();
        newUser.setLogin(user.getLogin());
        newUser.setUsername(user.getName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }


}
