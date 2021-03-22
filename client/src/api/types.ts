export interface UserResponse {
  id: number;
  email: string | null;
  firstName: string;
  lastName: string;
  age: number;
  city: string;
  allergies: string[];
  admin: boolean;
  blocked: boolean;
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
  expenses: string;
  addressLine: string;
  postCode: string;
  city: string;
  maxGuests: number;
  date: string;
  startTime: string;
  endTime: string;
}

export interface DinnerResponse {
  id: number;
  title: string;
  description: string;
  expenses: string;
  addressLine: string | null;
  postCode: string;
  city: string;
  date: string;
  startTime: string;
  endTime: string;
  maxPeople: number;
  host: UserResponse;
  guests: UserResponse[] | null;
}

export interface AdvertRequest {
  title: string;
  companyName: string;
  link: string;
  picture: string;
}

export interface AdvertResponse {
  id: number;
  title: string;
  companyName: string;
  link: string;
  picture: string;
}
