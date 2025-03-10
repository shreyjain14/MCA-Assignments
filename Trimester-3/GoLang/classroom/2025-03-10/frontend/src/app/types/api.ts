export interface User {
  id: number;
  username: string;
  email: string;
}

export interface Account {
  id: number;
  user_id: number;
  account_type: string;
  balance: number;
}

export interface CreateUserRequest {
  username: string;
  email: string;
  password: string;
}

export interface CreateAccountRequest {
  user_id: number;
  account_type: string;
}

export interface ErrorResponse {
  error: string;
} 