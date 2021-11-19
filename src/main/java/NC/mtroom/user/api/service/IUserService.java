package NC.mtroom.user.api.service;

import NC.mtroom.user.api.model.JwtRequest;
import NC.mtroom.user.api.model.JwtResponse;
import NC.mtroom.user.api.model.UserDto;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;

public interface IUserService {
    public UserEntity getUser(String login);
    public UserEntity registerUser(UserDto user);
    public JwtResponse loginUser(JwtRequest authenticationRequest) throws Exception;
}
