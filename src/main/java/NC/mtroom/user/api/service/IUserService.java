package NC.mtroom.user.api.service;

import NC.mtroom.user.api.model.*;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.service.UserService;

import java.util.List;

public interface IUserService {
    public UserEntity getUser(String login);
    public RegisterDto registerUser(UserDto user)throws Exception;
    public LoginDto loginUser(JwtRequest authenticationRequest) throws Exception;
    public List<UserHistoryDto> getUserHistory(String username);
    public Iterable<UserEntity> getAllUsers();
}
