package spiis.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spiis.server.TestUtil;
import spiis.server.api.LogInResponse;
import spiis.server.api.UserResponse;
import spiis.server.api.ValueWrapper;
import spiis.server.error.ForbiddenException;
import spiis.server.error.InvalidTokenException;
import spiis.server.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private AdminController adminController;
    @Autowired
    private TestUtil testUtil;
    @Autowired
    private UserService userService;

    @Test
    void testBootstrapAdmin() {
        LogInResponse testUser = testUtil.createdLoggedInUser();
        Long userId = testUser.getUser().getId();

        assertFalse(testUser.getUser().isAdmin());

        // First use the secret bootstrap token to make user 1 admin
        assertThrows(InvalidTokenException.class, () -> {
            adminController.makeMeAdmin(new ValueWrapper<>("wrong token"), testUser.getToken());
        });
        adminController.makeMeAdmin(new ValueWrapper<>(adminController.getAdminBootstrapToken()), testUser.getToken());

        // Get a fresh UserResponse for user1, which is now admin
        UserResponse adminUser = userService.makeUserResponse(userId, true);
        assertTrue(adminUser.isAdmin());

        // Make a new user, which is not admin by default
        LogInResponse testUser2 = testUtil.createdLoggedInUser();
        Long user2Id = testUser2.getUser().getId();

        assertFalse(testUser2.getUser().isAdmin());

        // Try making yourself admin when you are not admin (illegal!)
        assertThrows(ForbiddenException.class, () -> {
            adminController.setAdministrator(user2Id, new ValueWrapper<>(true), testUser2.getToken());
        });

        // The actual admin (user1) makes user2 admin
        adminController.setAdministrator(user2Id, new ValueWrapper<>(true), testUser.getToken());

        // Get a fresh UserResponse for user2, which is now admin
        UserResponse adminUser2 = userService.makeUserResponse(user2Id, true);
        assertTrue(adminUser2.isAdmin());

        // The user2 (now admin) removes admin from user1
        adminController.setAdministrator(userId, new ValueWrapper<>(false), testUser2.getToken());

        // Make sure user1 is no longer admin
        UserResponse normalUser = userService.makeUserResponse(userId, true);
        assertFalse(normalUser.isAdmin());

        testUtil.deleteUser(userId);
        testUtil.deleteUser(user2Id);
    }
}
