'use client';

import { useEffect, useState, use } from 'react';
import { useAtom } from 'jotai';
import { currentUserAtom } from '../../store/atoms';
import { accountService } from '../../services/api';
import { Account } from '../../types/api';
import { useRouter, useParams } from 'next/navigation';
import { toast } from 'react-toastify';
import Link from 'next/link';

export default function AccountDetails() {
  // Use the useParams hook instead of props
  const params = useParams();
  const [currentUser] = useAtom(currentUserAtom);
  const [account, setAccount] = useState<Account | null>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();
  
  // Convert id to number
  const accountId = params?.id ? parseInt(params.id as string) : 0;

  useEffect(() => {
    if (!accountId) {
      toast.error('Invalid account ID');
      router.push('/dashboard');
      return;
    }

    const fetchAccountData = async () => {
      try {
        setLoading(true);
        const accountData = await accountService.getAccount(accountId);
        setAccount(accountData);
      } catch (error) {
        console.error('Error fetching account data:', error);
        toast.error('Failed to load account information. Please try again.');
      } finally {
        setLoading(false);
      }
    };

    fetchAccountData();
  }, [accountId, router]);

  if (loading) {
    return (
      <div className="text-center py-8">
        <p>Loading account details...</p>
      </div>
    );
  }

  if (!account) {
    return (
      <div className="text-center py-8">
        <p className="mb-4">Account not found or you don't have access to this account.</p>
        <Link href="/dashboard" className="text-indigo-600 hover:underline">
          Return to Dashboard
        </Link>
      </div>
    );
  }

  // Check if the current user owns this account
  const isOwnAccount = currentUser && account.user_id === currentUser.id;

  return (
    <div className="max-w-4xl mx-auto mt-8">
      <div className="mb-8">
        <Link 
          href="/accounts" 
          className="text-indigo-600 hover:underline mb-4 inline-block"
        >
          ← Back to Accounts
        </Link>
        
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="flex justify-between items-start mb-4">
            <div>
              <h1 className="text-2xl font-bold text-indigo-700">
                {account.account_type} Account
              </h1>
              <p className="text-gray-500">Account #{account.id}</p>
            </div>
            <div className="text-right">
              <p className="text-sm text-gray-500 mb-1">Current Balance</p>
              <p className="text-3xl font-bold text-indigo-700">
                ${account.balance.toFixed(2)}
              </p>
            </div>
          </div>

          {account.account_type === 'Current' && (
            <div className="mb-4 p-4 bg-gray-50 rounded-md">
              <h2 className="text-sm font-medium text-gray-700 mb-2">Overdraft Information</h2>
              <p className="text-sm text-gray-600">
                This account has an overdraft facility of up to $200.
              </p>
            </div>
          )}

          {isOwnAccount && (
            <div className="flex flex-wrap gap-3 mt-6">
              <Link 
                href={`/accounts/${accountId}/deposit`}
                className="bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition"
              >
                Deposit Funds
              </Link>
              <Link 
                href={`/accounts/${accountId}/withdraw`}
                className="bg-white text-indigo-600 border border-indigo-600 py-2 px-4 rounded-md hover:bg-indigo-50 transition"
              >
                Withdraw Funds
              </Link>
            </div>
          )}
        </div>
      </div>
    </div>
  );
} 