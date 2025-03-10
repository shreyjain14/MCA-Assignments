'use client';

import { useEffect, useState } from 'react';
import { useAtom } from 'jotai';
import { currentUserAtom } from '../store/atoms';
import { accountService } from '../services/api';
import { Account } from '../types/api';
import Link from 'next/link';
import { toast } from 'react-toastify';

export default function AccountsPage() {
  const [currentUser] = useAtom(currentUserAtom);
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [loading, setLoading] = useState(true);
  const [viewMode, setViewMode] = useState<'user' | 'all'>(currentUser ? 'user' : 'all');

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        setLoading(true);
        if (viewMode === 'user' && currentUser) {
          // If viewing user's accounts and user is logged in
          const userAccounts = await accountService.getUserAccounts(currentUser.id);
          setAccounts(userAccounts);
        } else {
          // Otherwise, show all accounts
          const allAccounts = await accountService.getAllAccounts();
          setAccounts(allAccounts);
        }
      } catch (error) {
        console.error('Error fetching accounts:', error);
        toast.error('Failed to load accounts. Please try again.');
      } finally {
        setLoading(false);
      }
    };

    fetchAccounts();
  }, [currentUser, viewMode]);

  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-indigo-700 mb-4">Banking Accounts</h1>
        
        {loading ? (
          <div className="text-center py-8">
            <p>Loading accounts...</p>
          </div>
        ) : (
          <>
            <div className="flex justify-between items-center mb-6">
              <div className="flex items-center gap-4">
                {currentUser && (
                  <div className="flex rounded-md overflow-hidden border border-indigo-200">
                    <button 
                      onClick={() => setViewMode('user')}
                      className={`px-4 py-2 text-sm ${viewMode === 'user' 
                        ? 'bg-indigo-600 text-white' 
                        : 'bg-white text-indigo-600'}`}
                    >
                      My Accounts
                    </button>
                    <button 
                      onClick={() => setViewMode('all')}
                      className={`px-4 py-2 text-sm ${viewMode === 'all' 
                        ? 'bg-indigo-600 text-white' 
                        : 'bg-white text-indigo-600'}`}
                    >
                      All Accounts
                    </button>
                  </div>
                )}
                <p className="text-gray-600">
                  {viewMode === 'user' && currentUser 
                    ? `Showing accounts for ${currentUser.username}`
                    : 'Showing all accounts'}
                </p>
              </div>
              {currentUser && (
                <Link 
                  href="/accounts/create" 
                  className="bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition"
                >
                  Create New Account
                </Link>
              )}
            </div>

            {viewMode === 'user' && !currentUser ? (
              <div className="bg-indigo-50 p-6 rounded-lg text-center">
                <p className="mb-4">Please log in to view and manage your accounts.</p>
                <Link 
                  href="/login"
                  className="bg-indigo-600 text-white py-2 px-6 rounded-md hover:bg-indigo-700 transition inline-block"
                >
                  Login
                </Link>
              </div>
            ) : accounts.length === 0 ? (
              <div className="bg-white p-6 rounded-lg shadow-md text-center">
                <p className="mb-4">
                  {viewMode === 'user' 
                    ? "You don't have any accounts yet." 
                    : "No accounts found."}
                </p>
                {viewMode === 'user' && currentUser && (
                  <Link 
                    href="/accounts/create"
                    className="text-indigo-600 font-medium hover:underline"
                  >
                    Create your first account
                  </Link>
                )}
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {accounts.map((account) => (
                  <Link 
                    key={account.id}
                    href={`/accounts/${account.id}`} 
                    className="bg-white p-5 rounded-lg shadow-md cursor-pointer hover:shadow-lg transition"
                  >
                    <div className="flex justify-between items-center mb-2">
                      <h3 className="font-semibold">{account.account_type} Account</h3>
                      <span className="text-sm bg-gray-100 px-2 py-1 rounded">#{account.id}</span>
                    </div>
                    <p className="text-2xl font-bold text-indigo-700 mb-3">
                      ${account.balance.toFixed(2)}
                    </p>
                    <div className="flex justify-between items-center">
                      <span className="text-sm text-gray-500">Owner: User#{account.user_id}</span>
                      <span className="text-sm text-indigo-600">View Details →</span>
                    </div>
                  </Link>
                ))}
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
} 