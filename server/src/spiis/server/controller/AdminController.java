package spiis.server.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import spiis.server.api.ValueWrapper;
import spiis.server.error.ForbiddenException;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;
import spiis.server.service.AuthService;

@RestController
public class AdminController {

    private final AuthService authService;
    private final UserRepository userRepository;

    private final String sessionAdminBootstrapper;

    @Autowired
    public AdminController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.sessionAdminBootstrapper = authService.generateToken();
        LoggerFactory.getLogger(AdminController.class)
                .trace("This session's admin boostrap token is {}", sessionAdminBootstrapper);
    }

    /**
     * Set the admin status of a user, the body is either
     * {value: true} or {value: false}
     * Is only allowed if the caller is admin
     *
     * @param userId the id of the user we want to set
     * @param admin the object value to be set
     * @param token the auth token of the admin making the request
     */
    @PutMapping("/users/{userId}/admin")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setAdministrator(@PathVariable("userId") Long userId,
                                 @RequestBody ValueWrapper<Boolean> admin,
                                 @RequestHeader("Authorization") String token) {
        if (!authService.isTokenForAdminUser(token))
            throw new ForbiddenException();

        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.setAdmin(admin.getValue());
    }

    /**
     * Sets the requesting user to admin by providing the secret admin bootstrapping password
     *
     * @param bootstrap a value wrapper for the bootstrap token
     * @param token the auth token for the requesting user
     */
    @PostMapping("/makeMeAdmin")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeMeAdmin(@RequestBody ValueWrapper<String> bootstrap,
                            @RequestHeader("Authorization") String token) {
        if (!bootstrap.getValue().equals(this.sessionAdminBootstrapper))
            throw new InvalidTokenException();

        User user = authService.getUserForToken(token);
        user.setAdmin(true);
    }

    /**
     * The secret token used for bootstrapping admin accounts.
     * Must NOT be accessible from clients.
     */
    public String getAdminBootstrapToken() {
        return this.sessionAdminBootstrapper;
    }
}
