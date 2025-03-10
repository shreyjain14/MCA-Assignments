import { atom } from 'jotai';
import { User, Account } from '../types/api';

// User state
export const currentUserAtom = atom<User | null>(null);

// Selected account state
export const selectedAccountIdAtom = atom<number | null>(null);

// User accounts list
export const userAccountsAtom = atom<Account[]>([]); 