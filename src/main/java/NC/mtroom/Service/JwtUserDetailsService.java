package NC.mtroom.Service;


import NC.mtroom.DAO.UserDao;
import NC.mtroom.Dto.UserDto;
import NC.mtroom.Entity.DaoUser;
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
        DaoUser daoUser = userDao.findByUsername(username);
        if (daoUser == null ) {
            throw new UsernameNotFoundException("Пользователь с именем "+ username +" не найден ");
        }
        return new org.springframework.security.core.userdetails.User(daoUser.getUsername(), daoUser.getPassword(),
                new ArrayList<>());
    }

    public DaoUser save(UserDto user) {
        DaoUser newUser = new DaoUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }


}
