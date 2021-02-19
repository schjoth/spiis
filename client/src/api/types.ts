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

export interface SignUpRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  location: string;
  age: number;
  allergies: string[];
}
