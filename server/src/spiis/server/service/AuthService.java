package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.error.ForbiddenException;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.AuthToken;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {

    private static final int BCRYPT_STRENGTH = 10;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
     * @param token the token in question
     * @return userId of the User
     * @throws InvalidTokenException if the token is unrecognized
     */
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public User getUserForToken(@Nullable String token) {
        if (token == null)
            throw new InvalidTokenException();
        return userRepository.findUserByToken(token).orElseThrow(InvalidTokenException::new);
    }

    /**
     * Checks if a request with the given token has access to the given user.
     * If the token can't be used, an exception is thrown.
     * @param token the token
     * @param userId the id of the user in question
     * @throws InvalidTokenException if the token is unrecognized
     * @throws ForbiddenException if the token isn't authorized for the given user
     */
    @Transactional
    public void throwIfForbidden(@Nullable String token, Long userId) {
        if (token == null)
            throw new InvalidTokenException();
        User user = userRepository.findUserByToken(token).orElseThrow(InvalidTokenException::new);

        if (!userId.equals(user.getId()))
            throw new ForbiddenException();
    }
}
