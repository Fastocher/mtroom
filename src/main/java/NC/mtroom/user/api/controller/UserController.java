package NC.mtroom.user.api.controller;

import NC.mtroom.user.api.model.JwtRequest;
import NC.mtroom.user.api.model.UserDto;
import NC.mtroom.user.api.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
@Validated
@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.loginUser(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto userDto) throws Exception {
            return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @GetMapping
    public ResponseEntity<?> getUser (String login) throws UsernameNotFoundException {
            return ResponseEntity.ok(userService.getUser(login));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers () throws UsernameNotFoundException {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/history/{login}")
    public ResponseEntity<?> getHistory (@PathVariable @Pattern(regexp = "[a-z0-9A-Z_'^']+",message = "Not available characters for login! Only a-z, 0-9, '_' available") String login) throws RuntimeException {
        return ResponseEntity.ok(userService.getUserHistory(login));
    }

}


