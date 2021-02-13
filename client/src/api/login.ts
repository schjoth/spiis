// This file contains state and state manipulation for logging in and out

import { reactive, readonly } from "vue";
import client from "./client";
import type { LogInRequest, LogInResponse, User } from "./types";

interface LogInState {
  user: User | null;
  token: string | null;
  status: "loggedOut" | "loggingIn" | "loggedIn";
}

const defaultLogInState = (): LogInState => ({
  user: null,
  token: null,
  status: "loggedOut"
});

const logInState = reactive(defaultLogInState());

export const getLogInState = () => readonly(logInState);

/**
 * Try logging in with email and password.
 * Will log out first if already logged in.
 * Sets state to logged in if successful.
 * Saves the token to localStore.
 *
 * @param email
 * @param password
 * @throws error if logging in failed
 */
export async function logIn(email: string, password: string) {
  if (logInState.status != "loggedOut") logOut();
  logInState.status = "loggingIn";

  try {
    const request: LogInRequest = { email, password };
    const response: LogInResponse = await client.post("/login", request);
    logInState.status = "loggedIn";
    logInState.user = response.user;
    logInState.token = response.token;
    localStorage.setItem("token", response.token);
  } catch (error) {
    logOut(); //Failed to log in, reset state
    throw error;
  }
}

/**
 * Try logging in with a token.
 * Will log out first if already logged in.
 * If the token is valid, user info is retrieved from the server.
 * Saves the token to localStore.
 *
 * @param token
 */
export async function logInWithToken(token: string) {
  if (logInState.status != "loggedOut") logOut();
  logInState.status = "loggingIn";

  try {
    const config = { headers: { Authorization: token } };
    logInState.user = await client.post("/token/user", config);
    logInState.token = token;
    logInState.status = "loggedIn";
    localStorage.setItem("token", token);
  } catch (error) {
    logOut();
    throw error;
  }
}

/**
 * Removes user info and token, also from localStore.
 * Sets status to loggedOut.
 */
export function logOut() {
  logInState.user = null;
  logInState.token = null;
  logInState.status = "loggedOut";
  localStorage.removeItem("token");
}
