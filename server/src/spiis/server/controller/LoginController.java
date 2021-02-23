package spiis.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.LogInRequest;
import spiis.server.api.LogInResponse;
import spiis.server.api.SignUpRequest;
import spiis.server.api.UserResponse;
import spiis.server.service.AuthService;
import spiis.server.service.UserService;

@RestController
public class LoginController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public LoginController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Creates a new user and logs them in
     * @param request info needed to create the new user
     * @return LogInResponse with user info and token
     */
    @PostMapping("/signup")
    public LogInResponse signup(@RequestBody SignUpRequest request) {
        String encodedPassword = authService.encodePassword(request.getPassword());
        UserResponse user = userService.createUser(request, encodedPassword);
        String token = authService.makeTokenForUser(user.getId());
        return new LogInResponse(user, token);
    }

    /**
     * Logs in with email and password
     * @param request the credentials
     * @return LogInResponse with user info and token
     */
    @PostMapping("/login")
    public LogInResponse login(@RequestBody LogInRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    /**
     * Gets a UserResponse for the user who asks, based on the token
     * @param token the token
     * @return A UserResponse for the user
     */
    @GetMapping("/tokens/user")
    public UserResponse getUserFromToken(@RequestHeader("Authorization") String token) {
        return authService.getUserResponseForToken(token);
    }
}
