package NC.mtroom.Controller;

import NC.mtroom.Config.JwtTokenUtil;
import NC.mtroom.Dto.UserDto;
import NC.mtroom.Entity.UserEntity;
import NC.mtroom.Model.JwtRequest;
import NC.mtroom.Model.JwtResponse;
import NC.mtroom.Model.UserModel;
import NC.mtroom.Service.JwtUserDetailsService;
import NC.mtroom.Service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
@AllArgsConstructor

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDTO) throws Exception {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong login or password");
//        } else {
            return ResponseEntity.ok(userDetailsService.save(userDTO));
//        }
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

    @GetMapping("/user")
    public ResponseEntity<?> getUser (String login) throws Exception {
            return ResponseEntity.ok(userService.getuser(login));
    }

}



//    @PostMapping("/registration")
//    public UserModel registration(@RequestBody User user) {
//        try {
//            userService.registration(user);
//            //return ResponseEntity.ok("Пользователь успешно сохранен");
//            return UserModel.toModel(user);
//        } catch (UserAlreadyExist e) {
//            return null;
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
//    @PostMapping("/login")
//    public LoginModel login(@RequestBody User user){
//        try{
//
//        }catch (){
//
//        }
//    }
//}
