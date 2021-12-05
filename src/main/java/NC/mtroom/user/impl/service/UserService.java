package NC.mtroom.user.impl.service;

import NC.mtroom.JWTConfig.CustomUserDetails;
import NC.mtroom.JWTConfig.JwtTokenUtil;
import NC.mtroom.JWTConfig.Service.JwtUserDetailsService;
import NC.mtroom.room.api.model.TimeSegmentDto;
import NC.mtroom.user.api.model.*;
import NC.mtroom.user.api.service.IUserService;
import NC.mtroom.user.impl.entity.History;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;
import NC.mtroom.user.impl.repository.UserHistoryRepository;
import NC.mtroom.user.impl.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserHistoryRepository userHistoryRepository;
    private final JwtUserDetailsService jwtUserDetailsService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder bcryptEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       UserHistoryRepository userHistoryRepository,
                       JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userHistoryRepository = userHistoryRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public RegisterDto registerUser(UserDto userDto) throws Exception{
        UserEntity newUser = new UserEntity();
        newUser.setLogin(userDto.getLogin());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        userRepository.save(newUser);

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin(userDto.getLogin());
        jwtRequest.setPassword(userDto.getPassword());

        RegisterDto registerDto = new RegisterDto();
        registerDto.setLogin(userDto.getLogin());
        registerDto.setName(userDto.getUsername());
        registerDto.setToken(loginUser(jwtRequest).getToken());
        return registerDto;
    }


    @Override
    public LoginDto loginUser(JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());


        final CustomUserDetails customUserDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(customUserDetails);

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(new JwtResponse(token).getToken());
        loginDto.setName(customUserDetails.getName());

        return loginDto;
    }

    private void authenticate(String login, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @Override
    public List<UserHistoryDto> getUserHistory(String username) {
        LinkedList<UserHistoryDto> answer =  new LinkedList<>();

        UserEntity userEntity = userRepository.findByUsername(username);
        ArrayList<UserHistory> userHistoryList = (ArrayList<UserHistory>) userHistoryRepository.findAllByUserID(userEntity);

        for (UserHistory userHistory : userHistoryList) {
            UserHistoryDto currentDTO = new UserHistoryDto();

            History history = userHistory.getHistoryID();
            TimeSegmentDto time = new TimeSegmentDto(
                    history.getStart().toString(),
                    history.getEnd().toString(),
                    history.getHistoryID()
            );

            currentDTO.setRoom_uuid(history.getRoomID().getRoomID());
            currentDTO.setAdmin(userHistory.isAdmin());
            currentDTO.setTime(time);
            answer.add(currentDTO);
        }
        return answer;
    }

}
