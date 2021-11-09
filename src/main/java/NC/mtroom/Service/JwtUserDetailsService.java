package NC.mtroom.Service;


import NC.mtroom.Dao.UserDao;
import NC.mtroom.Dto.UserDto;
import NC.mtroom.Entity.UserEntity;
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
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByUsername(username);
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
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }


}
