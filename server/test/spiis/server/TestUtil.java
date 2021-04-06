package spiis.server;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spiis.server.api.*;
import spiis.server.controller.AdminController;
import spiis.server.controller.LoginController;
import spiis.server.error.InvalidTokenException;
import spiis.server.error.NotFoundException;
import spiis.server.model.User;
import spiis.server.repository.UserRepository;
import spiis.server.service.AuthService;
import spiis.server.service.DinnerService;
import spiis.server.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Service
public class TestUtil {

    @Autowired
    private LoginController loginController;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private DinnerService dinnerService;
    @Autowired
    AdminController adminController;

    public String randomName(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    public String randomAZ09(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public String randomNumString(int length) {
        return RandomStringUtils.random(length, false, true);
    }

    public String randomEmail() {
        return randomName(8) +
                '.' +
                randomName(8) +
                '@' +
                randomName(6) +
                '.' +
                randomName(3);
    }

    public int randomInt(int min, int max) {
        return RandomUtils.nextInt(min, max+1);
    }

    public SignUpRequest randomSignUpRequest() {
        SignUpRequest request = new SignUpRequest();
        request.setEmail(randomEmail());
        request.setPassword(randomAZ09(10));
        request.setFirstName(randomName(8));
        request.setLastName(randomName(8));
        request.setAge(randomInt(10, 100));
        request.setAllergies(List.of(randomName(8), randomName(12)));
        request.setCity(randomName(10));
        return request;
    }

    /**
     * Creates a user with random info, and logs them in, resulting in a UserResponse and a token
     * @return LogInResponse for the new logged in user
     */
    public LogInResponse createdLoggedInUser() {
        return loginController.signup(randomSignUpRequest());
    }

    /**
     * Creates a user with random info and admin access, and logs them in, resulting in a UserResponse and a token
     * @return LogInResponse for the new logged in admin
     */
    public LogInResponse createLoggedInAdmin() {
        LogInResponse testUser = createdLoggedInUser();
        Long userId = testUser.getUser().getId();

        // First use the secret bootstrap token to make user 1 admin
        adminController.makeMeAdmin(new ValueWrapper<>(adminController.getAdminBootstrapToken()), testUser.getToken());

        // Get a fresh UserResponse for user1, which is now admin
        UserResponse adminUser = userService.makeUserResponse(userId, true);

        return new LogInResponse(adminUser, testUser.getToken());
    }


    /**
     * Makes a token for a user, possibly invalidating previous tokens
     * @param userId the id of the user in question
     * @return the new token
     */
    public String makeTokenForUser(long userId) {
        return authService.makeTokenForUser(userId);
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    public DinnerRequest randomDinnerRequest() {
        DinnerRequest request = new DinnerRequest();
        request.setTitle(randomName(10));
        request.setDate("2022-12-03");
        request.setStartTime("18:00");
        request.setEndTime("20:00");
        request.setDescription(randomAZ09(30));
        request.setAddressLine(randomName(10));
        request.setPostCode(randomNumString(4));
        request.setCity(randomName(10));
        request.setMaxGuests(randomInt(2,10));
        request.setExpenses("None!");
        request.setRegistrationDeadlineDate("2022-11-03");
        request.setRegistrationDeadlineTime("17:00");
        return request;
    }

    @Transactional
    public DinnerResponse createTestDinner(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return dinnerService.createDinner(randomDinnerRequest(), user);
    }

    public void deleteDinner(Long dinnerId) {
        dinnerService.deleteDinner(dinnerId);
    }
}
