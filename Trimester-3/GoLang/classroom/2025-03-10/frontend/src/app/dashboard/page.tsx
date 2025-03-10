'use client';

import { useEffect, useState } from 'react';
import { useAtom } from 'jotai';
import { currentUserAtom, userAccountsAtom, selectedAccountIdAtom } from '../store/atoms';
import { accountService } from '../services/api';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { toast } from 'react-toastify';
import { Account } from '../types/api';

export default function Dashboard() {
  const [currentUser] = useAtom(currentUserAtom);
  const [userAccounts, setUserAccounts] = useAtom(userAccountsAtom);
  const [selectedAccountId, setSelectedAccountId] = useAtom(selectedAccountIdAtom);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    // Redirect if not logged in
    if (!currentUser) {
      router.push('/login');
      return;
    }

    // Fetch user accounts
    const fetchAccounts = async () => {
      try {
        setLoading(true);
        setError(null);
        const accounts = await accountService.getUserAccounts(currentUser.id);
        // Make sure accounts is an array (even if empty)
        setUserAccounts(Array.isArray(accounts) ? accounts : []);
      } catch (error) {
        console.error('Error fetching accounts:', error);
        // Set empty array for accounts to prevent undefined errors
        setUserAccounts([]);
        setError('Failed to load accounts. The API might be unavailable or there is a connection issue.');
        toast.error('Failed to load accounts. Please try again.');
      } finally {
        setLoading(false);
      }
    };

    fetchAccounts();
  }, [currentUser, router, setUserAccounts]);

  const handleAccountSelect = (account: Account) => {
    setSelectedAccountId(account.id);
    router.push(`/accounts/${account.id}`);
  };

  if (!currentUser) {
    return null; // This will be handled by the redirect in useEffect
  }

  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-indigo-700 mb-6">
          Welcome, {currentUser.username}!
        </h1>

        {error && (
          <div className="bg-red-50 border-l-4 border-red-400 p-4 mb-6">
            <p className="text-red-700">{error}</p>
          </div>
        )}

        {loading ? (
          <div className="text-center py-8">
            <p>Loading your accounts...</p>
          </div>
        ) : (
          <>
            <div className="mb-8">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-semibold">Your Accounts</h2>
                <Link 
                  href="/accounts/create" 
                  className="bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition"
                >
                  Create New Account
                </Link>
              </div>

              {userAccounts.length === 0 ? (
                <div className="bg-white p-6 rounded-lg shadow-md text-center">
                  <p className="mb-4">You don't have any accounts yet.</p>
                  <Link 
                    href="/accounts/create"
                    className="text-indigo-600 font-medium hover:underline"
                  >
                    Create your first account
                  </Link>
                </div>
              ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  {userAccounts.map((account) => (
                    <div
                      key={account.id}
                      onClick={() => handleAccountSelect(account)}
                      className="bg-white p-5 rounded-lg shadow-md cursor-pointer hover:shadow-lg transition"
                    >
                      <div className="flex justify-between items-center mb-2">
                        <h3 className="font-semibold">{account.account_type} Account</h3>
                        <span className="text-sm bg-gray-100 px-2 py-1 rounded">#{account.id}</span>
                      </div>
                      <p className="text-2xl font-bold text-indigo-700 mb-2">
                        ${account.balance.toFixed(2)}
                      </p>
                      {account.account_type === 'Current' && (
                        <p className="text-sm text-gray-600">
                          Includes overdraft facility up to $200
                        </p>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </div>
          </>
        )}
      </div>
    </div>
  );
} 