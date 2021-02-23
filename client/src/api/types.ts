export interface UserResponse {
  id: number;
  email: string | null;
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
  city: string;
  age: number;
  allergies: string[];
}

export interface DinnerRequest {
  title: string;
  description: string;
  addressLine: string;
  postCode: string;
  city: string;
  maxPeople: number;
  time: string;
}

export interface DinnerResponse {
  id: number;
  title: string;
  description: string;
  addressLine: string | null;
  postCode: string;
  city: string;
  time: string;
  maxPeople: number;
  host: UserResponse;
  guests: UserResponse[] | null;
}
