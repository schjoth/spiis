import { logIn, logInWithToken, logOut } from "@/api/login";
import { getLogInState } from "@/store/loginState";
import nock from "nock";
import { UserResponse } from "@/api/types";
import client, { DEFAULT_BASEURL } from "@/api/client";

const scope = nock(DEFAULT_BASEURL);

describe("login.ts", () => {
  beforeAll(() => {
    logOut();
    expect(getLogInState().status).toBe("loggedOut");
    expect(getLogInState().token).toBeNull();
    expect(getLogInState().user).toBeNull();
    expect(localStorage.getItem("token")).toBeNull();
  });

  const dummyUser: UserResponse = {
    id: 4,
    email: "test@example.com",
    firstName: "Per",
    lastName: "Fredrik",
    city: "Trondheim",
    age: 20,
    allergies: [],
    admin: false,
    blocked: false
  };
  const dummyToken = "HE845AXE2F9CE";

  it("gets user info and token from /login", async () => {
    scope
      .post("/login", { email: "test@example.com", password: "12345" })
      .reply(200, { user: dummyUser, token: dummyToken });

    await logIn("test@example.com", "12345");

    expect(getLogInState().status).toBe("loggedIn");
    expect(getLogInState().user).toMatchObject(dummyUser);
    expect(getLogInState().token).toBe(dummyToken);
    expect(localStorage.getItem("token")).toBe(dummyToken);

    scope
      .matchHeader("Authorization", dummyToken)
      .get("/test")
      .reply(200, { message: "yes" });

    const response: { message: string } = await client.get("/test");
    expect(response.message).toBe("yes");
  });

  it("can log in with an existing token", async () => {
    scope
      .matchHeader("Authorization", dummyToken)
      .get("/tokens/user")
      .reply(200, dummyUser);

    await logInWithToken(dummyToken);

    expect(getLogInState().status).toBe("loggedIn");
    expect(getLogInState().user).toMatchObject(dummyUser);
    expect(getLogInState().token).toBe(dummyToken);
    expect(localStorage.getItem("token")).toBe(dummyToken);
  });

  it("will throw when password is wrong", async () => {
    scope.post("/login").reply(401, { message: "Wrong email/password" });

    const logInCall = logIn("test@example.com", "12345");
    expect(getLogInState().status).toBe("loggingIn");
    await expect(logInCall).rejects.toHaveProperty(
      "message",
      "Wrong email/password"
    );
    expect(getLogInState().status).toBe("loggedOut");
  });
});
