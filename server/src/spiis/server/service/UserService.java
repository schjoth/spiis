package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.LogInResponse;
import spiis.server.api.SignUpRequest;
import spiis.server.api.UserResponse;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.ModelError;
import spiis.server.error.NotFoundException;
import spiis.server.model.Allergy;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    private List<String> makeAllergyList(User user) {
        return user.getAllergies().stream().map(Allergy::getAllergy).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public UserResponse makeUserResponse(User user) {
        Objects.requireNonNull(user.getId());
        return new UserResponse(user.getId(), user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getAge(), user.getLocation(), makeAllergyList(user));
    }

    @Transactional(readOnly = true)
    public UserResponse makeUserResponse(Long userId) {
        return makeUserResponse(userRepository.findById(userId).orElseThrow(NotFoundException::new));
    }

    /**
     * Creates a new user with the given information
     * @param request information about the new User
     * @return UserResponse for the new User
     * @throws ModelError if the resulting User object is invalid
     */
    @Transactional
    public UserResponse createUser(SignUpRequest request) {
        User.UserBuilder builder = new User.UserBuilder();
        builder.email(request.getEmail())
                .password(request.getPassword()) //TODO: Hash and salt the stored password
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .age(request.getAge())
                .location(request.getLocation());

        User user = builder.build();
        for (String allergy : request.getAllergies())
            user.addAllergy(new Allergy(allergy));

        userRepository.save(user);
        return makeUserResponse(user);
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
        if (!password.equals(user.getPassword()))
            throw new InvalidTokenException();

        String token = authService.makeTokenForUser(user.getId());
        UserResponse userResponse = makeUserResponse(user);
        return new LogInResponse(userResponse, token);
    }
}
