import { User } from '../types/api';

const STORAGE_KEY = 'banking_app_user';

export const authUtils = {
  saveUser: (user: User): void => {
    if (typeof window !== 'undefined') {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(user));
    }
  },

  getUser: (): User | null => {
    if (typeof window !== 'undefined') {
      try {
        const userData = localStorage.getItem(STORAGE_KEY);
        if (userData) {
          return JSON.parse(userData);
        }
      } catch (error) {
        console.error('Error parsing user data from localStorage:', error);
      }
    }
    return null;
  },

  clearUser: (): void => {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(STORAGE_KEY);
    }
  },

  isAuthenticated: (): boolean => {
    return !!authUtils.getUser();
  }
}; 