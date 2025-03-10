import axios from 'axios';
import { Account, CreateAccountRequest, CreateUserRequest, User } from '../types/api';

// Use Next.js API route as a proxy to avoid CORS issues
const API_BASE_URL = '/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Constants for account rules
const ACCOUNT_RULES = {
  CURRENT: {
    OVERDRAFT_LIMIT: -200, // Allow overdraft up to -$1000 for Current accounts
  },
};

export const userService = {
  createUser: async (data: CreateUserRequest): Promise<User> => {
    const response = await api.post('/users', data);
    return response.data;
  },
};

export const accountService = {
  createAccount: async (data: CreateAccountRequest) => {
    const response = await api.post('/accounts', data);
    return response.data;
  },
  
  // Get all accounts (mock implementation that will just get a few accounts)
  getAllAccounts: async (): Promise<Account[]> => {
    try {
      // In a real API, this would fetch all accounts
      // For this demo, we'll fetch a few sample accounts with IDs 1-10
      const accounts: Account[] = [];
      
      // Try to fetch a few accounts to populate the list
      for (let i = 1; i <= 10; i++) {
        try {
          const account = await accountService.getAccount(i);
          if (account) {
            accounts.push(account);
          }
        } catch (error) {
          // Just skip accounts that don't exist
        }
      }
      
      return accounts;
    } catch (error) {
      console.error('Error fetching all accounts:', error);
      return []; // Return empty array on error
    }
  },
  
  getUserAccounts: async (userId: number): Promise<Account[]> => {
    try {
      const response = await api.get(`/users/${userId}/accounts`);
      return Array.isArray(response.data) ? response.data : [];
    } catch (error) {
      console.error('Error fetching user accounts:', error);
      return []; // Return empty array on error
    }
  },
  
  getAccount: async (accountId: number): Promise<Account | null> => {
    try {
      const response = await api.get(`/accounts/${accountId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching account ${accountId}:`, error);
      return null; // Return null on error
    }
  },
  
  deposit: async (accountId: number, data: { amount: number }) => {
    const response = await api.post(`/accounts/${accountId}/deposit`, data);
    return response.data;
  },
  
  withdraw: async (accountId: number, data: { amount: number }) => {
    try {
      // First get the account to check its type and current balance
      const account = await accountService.getAccount(accountId);
      if (!account) {
        throw new Error('Account not found');
      }

      // Calculate the new balance after withdrawal
      const newBalance = account.balance - data.amount;

      // Check if withdrawal is allowed based on account type
      if (account.account_type === 'Current') {
        // For Current accounts, check against overdraft limit
        if (newBalance < ACCOUNT_RULES.CURRENT.OVERDRAFT_LIMIT) {
          throw new Error(`Withdrawal exceeds overdraft limit of $${Math.abs(ACCOUNT_RULES.CURRENT.OVERDRAFT_LIMIT)}`);
        }
      } else {
        // For Savings accounts, don't allow negative balance
        if (newBalance < 0) {
          throw new Error('Insufficient funds in Savings account');
        }
      }

      // If checks pass, proceed with withdrawal
      const response = await api.post(`/accounts/${accountId}/withdraw`, data);
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
      throw new Error('Failed to process withdrawal');
    }
  },
}; 