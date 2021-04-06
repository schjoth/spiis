package spiis.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spiis.server.AssertUtil;
import spiis.server.TestUtil;
import spiis.server.api.*;
import spiis.server.error.ConflictException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DinnerControllerTest {

    private final ValueWrapper<Boolean> valueWrapper = new ValueWrapper<>();
    @Autowired
    private TestUtil testUtil;
    @Autowired
    private DinnerController dinnerController;

    @Test
    public void getDinnerTest() {
        LogInResponse logInResponse = testUtil.createdLoggedInUser();
        UserResponse user = logInResponse.getUser();
        DinnerResponse dinner = testUtil.createTestDinner(user.getId());

        DinnerResponse requestedDinner = dinnerController.getDinner(dinner.getId());
        assertEquals(dinner.getId(), requestedDinner.getId());
        assertEquals(dinner.getTitle(), requestedDinner.getTitle());
        assertEquals(dinner.getDescription(), requestedDinner.getDescription());
        assertEquals(dinner.getExpenses(), requestedDinner.getExpenses());
        assertEquals(dinner.getDate(), requestedDinner.getDate());
        assertEquals(dinner.getStartTime(), requestedDinner.getStartTime());
        assertEquals(dinner.getEndTime(), requestedDinner.getEndTime());
        assertEquals(dinner.getAddressLine(), requestedDinner.getAddressLine());
        assertEquals(dinner.getPostCode(), requestedDinner.getPostCode());
        assertEquals(dinner.getCity(), requestedDinner.getCity());
        assertEquals(dinner.getMaxGuests(), requestedDinner.getMaxGuests());
        assertEquals(dinner.isCancelled(), requestedDinner.isCancelled());
        assertEquals(dinner.getHost(), requestedDinner.getHost());
        assertTrue(AssertUtil.listsContainSame(dinner.getGuests(), requestedDinner.getGuests()));
        testUtil.deleteDinner(dinner.getId());
    }

    @Test
    void getAllDinnersTest() {
        LogInResponse logInResponse = testUtil.createdLoggedInUser();
        UserResponse user = logInResponse.getUser();

        DinnerResponse dinner1 = testUtil.createTestDinner(user.getId());
        DinnerResponse dinner2 = testUtil.createTestDinner(user.getId());
        DinnerResponse dinner3 = testUtil.createTestDinner(user.getId());

        List<DinnerResponse> allDinners = dinnerController.getAllDinners();
        assertTrue(allDinners.stream().anyMatch(i -> i.getId().equals(dinner1.getId())));
        assertTrue(allDinners.stream().anyMatch(i -> i.getId().equals(dinner2.getId())));
        assertTrue(allDinners.stream().anyMatch(i -> i.getId().equals(dinner3.getId())));

        testUtil.deleteDinner(dinner1.getId());
        testUtil.deleteDinner(dinner2.getId());
        testUtil.deleteDinner(dinner3.getId());

        testUtil.deleteUser(user.getId());
    }

    @Test
    public void createDinnerTest() {
        LogInResponse logInResponse = testUtil.createdLoggedInUser();
        DinnerRequest dinnerRequest = testUtil.randomDinnerRequest();

        DinnerResponse dinnerResponse = dinnerController.createDinner(dinnerRequest, logInResponse.getToken()).getBody();
        assertNotNull(dinnerResponse);
        DinnerResponse dinner = dinnerController.getDinner(dinnerResponse.getId());
        assertEquals(dinnerResponse, dinner);
        testUtil.deleteDinner(dinnerResponse.getId());
    }

    @Test
    void editDinnerTest() {
        LogInResponse logInResponse = testUtil.createdLoggedInUser();
        UserResponse user = logInResponse.getUser();

        DinnerResponse dinner = testUtil.createTestDinner(user.getId());
        DinnerRequest dinnerRequest = testUtil.randomDinnerRequest();

        dinnerController.editDinner(dinner.getId(), dinnerRequest, logInResponse.getToken());
        DinnerResponse editedDinner = dinnerController.getDinner(dinner.getId());
        assertEquals(dinner.getId(), editedDinner.getId());
        assertEquals(dinnerRequest.getTitle(), editedDinner.getTitle());
        assertEquals(dinnerRequest.getDescription(), editedDinner.getDescription());
        assertEquals(dinnerRequest.getExpenses(), editedDinner.getExpenses());
        assertEquals(dinnerRequest.getDate(), editedDinner.getDate());
        assertEquals(dinnerRequest.getStartTime(), editedDinner.getStartTime());
        assertEquals(dinnerRequest.getEndTime(), editedDinner.getEndTime());
        assertEquals(dinnerRequest.getAddressLine(), editedDinner.getAddressLine());
        assertEquals(dinnerRequest.getPostCode(), editedDinner.getPostCode());
        assertEquals(dinnerRequest.getCity(), editedDinner.getCity());
        assertEquals(dinnerRequest.getMaxGuests(), editedDinner.getMaxGuests());
        assertEquals(dinner.isCancelled(), editedDinner.isCancelled());
        assertEquals(dinner.getHost(), editedDinner.getHost());
        assertEquals(dinner.getGuests(), editedDinner.getGuests());
        assertTrue(AssertUtil.listsContainSame(dinner.getGuests(), editedDinner.getGuests()));
        testUtil.deleteDinner(editedDinner.getId());
    }

    @Test
    public void setDinnerCancelledTest() {
        LogInResponse logInResponse = testUtil.createdLoggedInUser();
        UserResponse user = logInResponse.getUser();

        DinnerResponse dinner = testUtil.createTestDinner(user.getId());
        DinnerResponse response = dinnerController.getDinner(dinner.getId());
        assertFalse(response.isCancelled());

        valueWrapper.setValue(true);
        dinnerController.setDinnerCancelled(response.getId(), valueWrapper, logInResponse.getToken());
        DinnerResponse response2 = dinnerController.getDinner(dinner.getId());
        assertTrue(response2.isCancelled());

        testUtil.deleteDinner(dinner.getId());
        testUtil.deleteUser(user.getId());
    }

    @Test
    void addGuestToDinnerTest() {
        LogInResponse logInResponse1 = testUtil.createdLoggedInUser();
        UserResponse user1 = logInResponse1.getUser();
        DinnerRequest request = testUtil.randomDinnerRequest();
        request.setMaxGuests(2);
        DinnerResponse dinner = dinnerController.createDinner(request, logInResponse1.getToken()).getBody();

        LogInResponse logInResponse2 = testUtil.createdLoggedInUser();
        UserResponse user2 = logInResponse2.getUser();

        dinnerController.addGuestToDinner(dinner.getId(), user2.getId(), logInResponse2.getToken());
        DinnerResponse dinnerResponse = dinnerController.getDinner(dinner.getId());
        assertEquals(1, dinnerResponse.getGuests().size());

        assertThrows(ConflictException.class, () -> dinnerController.addGuestToDinner(dinner.getId(), user1.getId(), logInResponse1.getToken()));

        assertThrows(ConflictException.class, () -> dinnerController.addGuestToDinner(dinner.getId(), user2.getId(), logInResponse2.getToken()));

        assertThrows(ConflictException.class, () -> {
            LogInResponse logInResponse3 = testUtil.createdLoggedInUser();
            UserResponse user3 = logInResponse3.getUser();
            dinnerController.addGuestToDinner(dinner.getId(), user3.getId(), logInResponse3.getToken());
            LogInResponse logInResponse4 = testUtil.createdLoggedInUser();
            UserResponse user4 = logInResponse4.getUser();
            dinnerController.addGuestToDinner(dinner.getId(), user4.getId(), logInResponse4.getToken());
        });
        testUtil.deleteDinner(dinner.getId());
    }

    @Test
    void removeGuestFromDinnerTest() {
        LogInResponse logInResponse1 = testUtil.createdLoggedInUser();
        UserResponse host = logInResponse1.getUser();
        DinnerResponse dinner = testUtil.createTestDinner(host.getId());

        LogInResponse logInResponse2 = testUtil.createdLoggedInUser();
        UserResponse guest = logInResponse2.getUser();
        dinnerController.addGuestToDinner(dinner.getId(), guest.getId(), logInResponse2.getToken());
        DinnerResponse dinnerResponse = dinnerController.getDinner(dinner.getId());
        assertEquals(1, dinnerResponse.getGuests().size());

        dinnerController.removeGuestFromDinner(dinner.getId(), guest.getId(), logInResponse2.getToken());
        DinnerResponse dinnerResponse2 = dinnerController.getDinner(dinner.getId());
        assertEquals(0, dinnerResponse2.getGuests().size());

        testUtil.deleteDinner(dinner.getId());
    }
}
