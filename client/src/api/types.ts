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
  averageHostRating: number | null;
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
  registrationDeadlineDate: string;
  registrationDeadlineTime: string;
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
  cancelled: boolean;
  registrationDeadlineDate: string;
  registrationDeadlineTime: string;
  lockedByAdmin: boolean;
  host: UserResponse;
  guests: UserResponse[] | null;
  createdTime: string;
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

export type CommentVisibility = "host_only" | "guests_only" | "public";

export interface CommentRequest {
  content: string;
  visibility: CommentVisibility;
}

export interface CommentResponse {
  commentId: number;
  content: string | null; //null if deleted
  dinnerId: number;
  visibility: CommentVisibility;
  userId: number | null; //null if deleted
  userLastName: string | null;
  userFirstName: string | null;
  postedAt: string;
  editedAt: string;

  replies: CommentReplyResponse[];
}

export interface CommentReplyRequest {
  value: string;
}

export interface CommentReplyResponse {
  replyId: number;
  content: string | null;
  userId: number | null;
  userFirstName: string | null;
  userLastName: string | null;
  postedAt: string;
  editedAt: string;
}
