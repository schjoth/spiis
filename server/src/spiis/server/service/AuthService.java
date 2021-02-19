package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.error.ForbiddenException;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.AuthToken;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
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
     * Gets the id of the User that owns the token
     * @param token the token in question
     * @return userId of the User
     * @throws InvalidTokenException if the token is unrecognized
     */
    @Transactional
    public Long getUserIdForToken(@Nullable String token) {
        if (token == null)
            throw new InvalidTokenException();
        User user = userRepository.findUserByToken(token).orElseThrow(InvalidTokenException::new);
        return Objects.requireNonNull(user.getId());
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
