package spiis.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.LogInResponse;
import spiis.server.api.SignUpRequest;
import spiis.server.api.UserResponse;
import spiis.server.error.ConflictException;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.ModelError;
import spiis.server.error.NotFoundException;
import spiis.server.model.Allergy;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private List<String> makeAllergyList(User user) {
        return user.getAllergies().stream().map(Allergy::getAllergy).collect(Collectors.toList());
    }

    /**
     * Makes a serializable object containing info about the User.
     * If the response is intended for the user itself, extra info can be included
     * @param user the user we want info about
     * @param yourself if the response is for the user itself
     * @return UserResponse the info
     */
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public UserResponse makeUserResponse(User user, boolean yourself) {
        Objects.requireNonNull(user.getId());
        UserResponse.UserResponseBuilder builder = UserResponse.builder();
        builder.id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .city(user.getCity())
                .allergies(makeAllergyList(user))
                .admin(user.isAdmin())
                .blocked(user.isBlocked());

        if (yourself)
            builder.email(user.getEmail());

        return builder.build();
    }

    @Transactional(readOnly = true)
    public UserResponse makeUserResponse(Long userId, boolean yourself) {
        return makeUserResponse(userRepository.findById(userId).orElseThrow(NotFoundException::new), yourself);
    }

    /**
     * Make UserResponse objects for every user.
     * @return the list of response objects
     */
    @Transactional(readOnly = true)
    public List<UserResponse> makeUserResponses() {
        List<UserResponse> responses = new ArrayList<>();
        for (User user : userRepository.findAll())
            responses.add(makeUserResponse(user, false));
        return responses;
    }

    /**
     * Creates a new user with the given information
     * @param request information about the new User
     * @return UserResponse for the new User
     * @throws ModelError if the resulting User object is invalid
     */
    @Transactional
    public UserResponse createUser(SignUpRequest request, String encodedPassword) {
        String email = request.getEmail().toLowerCase();
        if (userRepository.findUserByEmail(email).isPresent())
            throw new ConflictException("Email is taken");

        User user = new User();
        user.setEmail(request.getEmail().trim());
        user.setPassword(encodedPassword);
        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setCity(request.getCity().trim());
        user.setAge(request.getAge());
        user.setAdmin(false);
        user.setBlocked(false);

        for (String allergy : request.getAllergies())
            user.addAllergy(new Allergy(allergy));

        user.verifyModel();
        userRepository.save(user);
        return makeUserResponse(user, true);
    }

    /**
     * Removes a user from the database.
     * Will in turn remove any relationships the user has, possibly deleting other things
     * @param user the user to be deleted
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        deleteUser(userRepository.findById(userId).orElseThrow(NotFoundException::new));
    }
}
