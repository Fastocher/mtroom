package NC.mtroom.user.api.controller;

import NC.mtroom.user.api.model.JwtRequest;
import NC.mtroom.user.api.model.UserDto;
import NC.mtroom.user.api.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.loginUser(authenticationRequest));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
            return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser (String login) throws UsernameNotFoundException {
            return ResponseEntity.ok(userService.getUser(login));
    }
}


