export interface User {
  userId: number;
  firstName: number;
  lastName: number;
}

export interface LogInRequest {
  email: string;
  password: string;
}

export interface LogInResponse {
  user: User;
  token: string;
}
