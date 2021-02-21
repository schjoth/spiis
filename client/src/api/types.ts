export interface UserResponse {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  age: number;
  location: string;
  allergies: string[];
}

export interface LogInRequest {
  email: string;
  password: string;
}

export interface LogInResponse {
  user: UserResponse;
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
