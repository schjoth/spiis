package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.LogInRequest;
import spiis.server.api.LogInResponse;
import spiis.server.api.UserResponse;
import spiis.server.error.ForbiddenException;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.AuthToken;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final int BCRYPT_STRENGTH = 10;

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.bcryptPasswordEncoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH, new SecureRandom());
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Hashes a password using bcrypt, using a salt
     * @param rawPassword the password as received from the user
     * @return the hashed and salted password, including the salt and meta-info
     */
    public String encodePassword(String rawPassword) {
        return bcryptPasswordEncoder.encode(rawPassword);
    }

    /**
     * Checks if a given password matches a salted and hashed password from the database
     * @param rawPassword the raw password
     * @param encodedPassword the salted and hashed password
     * @return true if match
     */
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Creates a new authorization token for the given user.
     * Can invalidate previous tokens.
     *
     * @param userId the id of the user
     * @return the new token for the user
     * @throws NotFoundException if no user exists with the given id
     */
    @Transactional
    public String makeTokenForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        String tokenText = generateToken();
        AuthToken token = new AuthToken(tokenText);

        user.setToken(token);
        return tokenText;
    }

    /**
     * Gets the User that owns the token
     * @param token the token
     * @return userId of the User
     * @throws InvalidTokenException if the token is null/unrecognized
     */
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public User getUserForToken(@Nullable String token) {
        if (token == null)
            throw new InvalidTokenException();
        return userRepository.findUserByToken(token).orElseThrow(InvalidTokenException::new);
    }

    /**
     * Logs in a user with email and password.
     * @param email the user's email
     * @param password the password used to log in
     * @throws InvalidTokenException if email or password is incorrect
     */
    @Transactional
    public LogInResponse login(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElseThrow(InvalidTokenException::new);
        Objects.requireNonNull(user.getId());

        if (!passwordMatches(password, user.getPassword()))
            throw new InvalidTokenException();

        String token = makeTokenForUser(user.getId());
        UserResponse userResponse = userService.makeUserResponse(user, true);
        return new LogInResponse(userResponse, token);
    }

    /**
     * Checks if the given token has access on behalf of the user
     * @param token the token
     * @param userId the id of the user
     * @return false if the token is null or doesn't give access
     * @throws InvalidTokenException if the token is not null, but still invalid
     */
    @Transactional(readOnly = true)
    public boolean doesTokenGiveAccess(@Nullable String token, Long userId) {
        if (token == null)
            return false;

        User tokenUser = getUserForToken(token);
        return userId.equals(tokenUser.getId());
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public boolean doesTokenGiveAccess(@Nullable String token, User user) {
        return doesTokenGiveAccess(token, Objects.requireNonNull(user.getId()));
    }

    /**
     * Throws if the token doesn't give access to the user
     * @param token the token
     * @param userId the userId
     * @throws InvalidTokenException if the token null or unrecognized
     * @throws ForbiddenException if the token is valid but doesn't have access
     */
    @Transactional(readOnly = true)
    public void throwIfForbidden(@Nullable String token, Long userId) {
        if (token == null)
            throw new InvalidTokenException();
        if (!doesTokenGiveAccess(token, userId))
            throw new ForbiddenException();
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public void throwIfForbidden(@Nullable String token, User users) {
        throwIfForbidden(token, Objects.requireNonNull(users.getId()));
    }
}
