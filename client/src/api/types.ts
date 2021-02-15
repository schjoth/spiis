export interface User {
  userId: number;
  firstName: string;
  lastName: string;
}

export interface LogInRequest {
  email: string;
  password: string;
}

export interface LogInResponse {
  user: User;
  token: string;
}
