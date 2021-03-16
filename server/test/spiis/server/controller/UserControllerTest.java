package spiis.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spiis.server.AssertUtil;
import spiis.server.api.*;
import spiis.server.model.User;
import spiis.server.service.AuthService;
import spiis.server.TestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private TestUtil testUtil;
    @Autowired
    private UserController userController;
    @Autowired
    private AuthService authService;


    @Test
    void getAllUsersTest() {
        LogInResponse user1 = testUtil.createdLoggedInUser();
        LogInResponse user2 = testUtil.createdLoggedInUser();
        LogInResponse user3 = testUtil.createdLoggedInUser();

        List<UserResponse> allUsers = userController.getAllUsers();
        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(user1.getUser().getId())));
        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(user2.getUser().getId())));
        assertTrue(allUsers.stream().anyMatch(it -> it.getId().equals(user3.getUser().getId())));

        testUtil.deleteUser(user1.getUser().getId());
        testUtil.deleteUser(user2.getUser().getId());
        testUtil.deleteUser(user3.getUser().getId());
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
    }

    private void assertTrue(boolean anyMatch) {
    }

    @Test
    void getHostingForUserTest() {
        LogInResponse initial = testUtil.createdLoggedInUser();
        long userId = initial.getUser().getId();
        String firstToken = initial.getToken();

        DinnerResponse dinnerResponse1 = testUtil.createTestDinner(userId);
        List<DinnerResponse> dinners = userController.getHostingForUser(userId, firstToken);

        assertTrue(dinners.stream().anyMatch(it -> it.getId() == dinnerResponse1.getId()));

        testUtil.deleteDinner(dinnerResponse1.getId());
    }

}
