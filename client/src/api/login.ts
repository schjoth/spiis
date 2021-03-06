import client from "./client";
import type {
  LogInRequest,
  LogInResponse,
  SignUpRequest,
  UserResponse
} from "./types";
import { setLoggedIn, setLoggedOut, setLoggingIn } from "@/store/loginState";

/**
 * Try logging in with email and password.
 * Will log out first if already logged in.
 * Sets state to logged in if successful.
 * Saves the token to localStorage.
 *
 * @param email
 * @param password
 * @throws error if logging in failed
 */
export async function logIn(email: string, password: string) {
  setLoggingIn();

  try {
    const request: LogInRequest = { email, password };
    const response: LogInResponse = await client.post("/login", request);
    setLoggedIn(response.user, response.token);
  } catch (error) {
    await logOut(); //Failed to log in, reset state
    throw error;
  }
}

/**
 * Try logging in with a token.
 * Will log out first if already logged in.
 * If the token is valid, user info is retrieved from the server.
 * Saves the token to localStorage.
 *
 * @param token
 */
export async function logInWithToken(token: string) {
  setLoggingIn();

  try {
    const config = { headers: { Authorization: token } };
    const user: UserResponse = await client.get("/tokens/user", config);
    setLoggedIn(user, token);
  } catch (error) {
    await logOut();
    throw error;
  }
}

/**
 * If we have a token in localStorage, try signing in with it
 */
export async function tryReusingToken() {
  const token = localStorage.getItem("token");
  if (token != null) await logInWithToken(token);
}

/**
 * Removes user info and token, also from localStorage.
 * Sets status to loggedOut.
 */
export async function logOut() {
  setLoggedOut();
}

/**
 * Creates a new user on the Server, and logs in if successful
 * @param request
 */
export async function signUp(request: SignUpRequest) {
  setLoggingIn();

  try {
    const response: LogInResponse = await client.post("/signup", request);
    setLoggedIn(response.user, response.token);
  } catch (error) {
    await logOut(); //Failed to log in, reset state
    throw error;
  }
}
