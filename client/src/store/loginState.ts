import { User } from "@/api/types";
import { reactive, readonly } from "vue";

interface LogInState {
  user: User | null;
  token: string | null;
  status: "loggedOut" | "loggingIn" | "loggedIn";
}

const defaultLogInState = (): LogInState => {
  return {
    user: null,
    token: null,
    status: "loggedOut"
  };
};

const logInState = reactive(defaultLogInState());

export const getLogInState = () => readonly(logInState);

export function setLoggedIn(user: User, token: string) {
  logInState.user = user;
  logInState.token = token;
  logInState.status = "loggedIn";
  localStorage.setItem("token", token);
}

export function setLoggingIn() {
  logInState.user = null;
  logInState.token = null;
  logInState.status = "loggingIn";
  localStorage.removeItem("token");
}

export function setLoggedOut() {
  logInState.user = null;
  logInState.token = null;
  logInState.status = "loggedOut";
  localStorage.removeItem("token");
}
