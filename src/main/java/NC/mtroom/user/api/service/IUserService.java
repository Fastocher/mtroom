package NC.mtroom.user.api.service;

import NC.mtroom.user.api.model.*;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    public UserEntity getUser(String login);
    public LoginDto registerUser(UserDto user)throws Exception;
    public JwtResponse loginUser(JwtRequest authenticationRequest) throws Exception;
    public List<UserHistoryDto> getUserHistory(String username);
}
