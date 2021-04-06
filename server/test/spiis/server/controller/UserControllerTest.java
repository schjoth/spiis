package spiis.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spiis.server.AssertUtil;
import spiis.server.api.*;
import spiis.server.error.ForbiddenException;
import spiis.server.service.AuthService;
import spiis.server.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private TestUtil testUtil;
    @Autowired
    private UserController userController;
    @Autowired
    private AuthService authService;

    @Test
    void getAllUsersTestForAdmin() {
        LogInResponse user1 = testUtil.createdLoggedInUser();
        LogInResponse user2 = testUtil.createdLoggedInUser();
        LogInResponse user3 = testUtil.createdLoggedInUser();
        LogInResponse admin = testUtil.createLoggedInAdmin();

        List<UserResponse> allUsers = userController.getAllUsers(admin.getToken());

        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(user1.getUser().getId())));
        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(user2.getUser().getId())));
        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(user3.getUser().getId())));
        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(admin.getUser().getId())));

        testUtil.deleteUser(user1.getUser().getId());
        testUtil.deleteUser(user2.getUser().getId());
        testUtil.deleteUser(user3.getUser().getId());
        testUtil.deleteUser(admin.getUser().getId());
    }

    @Test
    void getAllUsersTestForUser() {
        LogInResponse user1 = testUtil.createdLoggedInUser();
        assertThrows(ForbiddenException.class, () -> {userController.getAllUsers(user1.getToken());});
        testUtil.deleteUser(user1.getUser().getId());
    }

    @Test
    void getUserTest() {
        LogInResponse response= testUtil.createdLoggedInUser();
        UserResponse user1 = response.getUser();
        UserResponse user = userController.getUser(user1.getId(), response.getToken());
        assertEquals(user1.getAge(), user.getAge());
        assertEquals(user1.getId(), user.getId());
        assertTrue(AssertUtil.listsContainSame(user1.getAllergies(), user.getAllergies()));
        assertEquals(user1.getEmail(), user.getEmail());
        assertEquals(user1.getCity(), user.getCity());
        assertEquals(user1.getFirstName(), user.getFirstName());
        assertEquals(user1.getLastName(), user.getLastName());
        assertNull(user1.getAverageHostRating());
    }

    @Test
    void getHostingForUserTest() {
        LogInResponse initial = testUtil.createdLoggedInUser();
        long userId = initial.getUser().getId();
        String firstToken = initial.getToken();

        DinnerResponse dinnerResponse1 = testUtil.createTestDinner(userId);
        List<DinnerResponse> dinners = userController.getHostingForUser(userId, firstToken);

        assertTrue(dinners.stream().anyMatch(it -> it.getId().equals(dinnerResponse1.getId())));

        testUtil.deleteDinner(dinnerResponse1.getId());
    }
}
