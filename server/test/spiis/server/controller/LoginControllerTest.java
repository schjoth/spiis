package spiis.server.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spiis.server.AssertUtil;
import spiis.server.TestUtil;
import spiis.server.api.LogInRequest;
import spiis.server.api.LogInResponse;
import spiis.server.api.SignUpRequest;
import spiis.server.api.UserResponse;
import spiis.server.error.InvalidTokenException;
import spiis.server.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginControllerTest {

    @Autowired
    private TestUtil testUtil;
    @Autowired
    private LoginController loginController;
    @Autowired
    private AuthService authService;

    @Test
    void signupTest() {
        SignUpRequest request = testUtil.randomSignUpRequest();
        LogInResponse response = loginController.signup(request);

        String token = response.getToken();
        UserResponse user = response.getUser();
        authService.throwIfForbidden(token, user.getId());
        assertTrue(authService.doesTokenGiveAccess(token, user.getId()));

        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getAge(), user.getAge());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getCity(), user.getCity());
        assertTrue(AssertUtil.listsContainSame(request.getAllergies(), user.getAllergies()));

        // We don't sign up as admin (!)
        assertFalse(user.isAdmin());

        testUtil.deleteUser(user.getId());
    }

    @Test
    void loginTest() {
        SignUpRequest signup = testUtil.randomSignUpRequest();
        LogInResponse initial = loginController.signup(signup);
        long userId = initial.getUser().getId();

        String firstToken = initial.getToken();

        authService.throwIfForbidden(firstToken, userId);

        assertThrows(InvalidTokenException.class, () -> {
            LogInRequest request = new LogInRequest(signup.getEmail(), "Wrong Pass");
            loginController.login(request);
        });

        assertThrows(InvalidTokenException.class, () -> {
            LogInRequest request = new LogInRequest("Wrong email", signup.getPassword());
            loginController.login(request);
        });

        LogInRequest request = new LogInRequest(signup.getEmail(), signup.getPassword());
        LogInResponse login = loginController.login(request);

        // Check that only the new token is valid
        assertTrue(authService.doesTokenGiveAccess(login.getToken(), userId));
        assertThrows(InvalidTokenException.class, () -> authService.doesTokenGiveAccess(firstToken, userId));

        testUtil.deleteUser(userId);
    }

    @Test
    void getUserFromTokenTest() {
        LogInResponse response = testUtil.createdLoggedInUser();
        long userId = response.getUser().getId();

        // We try re-requesting the user with the token
        assertThrows(InvalidTokenException.class, () -> loginController.getUserFromToken("wrong token"));
        assertEquals(userId, loginController.getUserFromToken(response.getToken()).getId());

        // We give the user a new token
        String newToken = testUtil.makeTokenForUser(userId);

        // We must now use the new token when re-requesting the user
        assertThrows(InvalidTokenException.class, () -> loginController.getUserFromToken(response.getToken()));
        assertEquals(userId, loginController.getUserFromToken(newToken).getId());

        testUtil.deleteUser(userId);
    }
}
