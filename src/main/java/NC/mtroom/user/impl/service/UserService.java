package NC.mtroom.user.impl.service;

import NC.mtroom.JWTConfig.JwtTokenUtil;
import NC.mtroom.room.api.model.TimeSegmentDto;
import NC.mtroom.user.api.model.JwtRequest;
import NC.mtroom.user.api.model.JwtResponse;
import NC.mtroom.user.api.model.UserDto;
import NC.mtroom.user.api.model.UserHistoryDto;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public UserService(UserRepository userRepository,
                       PasswordEncoder bcryptEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       UserHistoryRepository userHistoryRepository) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userHistoryRepository = userHistoryRepository;
    }

    @Override
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity registerUser(UserDto user) {
        UserEntity newUser = new UserEntity();
        newUser.setLogin(user.getLogin());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public JwtResponse loginUser(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null ) {
            throw new UsernameNotFoundException("Пользователь с именем "+ username +" не найден ");
        }
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(),
                new ArrayList<>());
    }

    @Override
    public List<UserHistoryDto> getUserHistory(String username) {
        LinkedList<UserHistoryDto> answer =  new LinkedList<>();

        UserEntity userEntity = userRepository.findByUsername(username);
        ArrayList<UserHistory> userHistoryList = (ArrayList<UserHistory>) userHistoryRepository.findAllByUserID(userEntity);

        for (UserHistory userHistory : userHistoryList) {
            UserHistoryDto currentDTO = new UserHistoryDto();

            History history = userHistory.getHistoryID();
            Instant instantStart = Instant.ofEpochMilli(history.getStart().getTime());
            Instant instantEnd = Instant.ofEpochMilli(history.getEnd().getTime());
            TimeSegmentDto time = new TimeSegmentDto(
                    instantStart.toString(),
                    instantEnd.toString()
            );

            currentDTO.setRoom_uuid(history.getRoomID().getRoomID());
            currentDTO.setAdmin(userHistory.isOrg());
            currentDTO.setTime(time);
            answer.add(currentDTO);
        }
        return answer;
    }

}
