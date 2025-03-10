'use client';

import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useAtom } from 'jotai';
import { currentUserAtom } from '../../../store/atoms';
import { accountService } from '../../../services/api';
import { useRouter, useParams } from 'next/navigation';
import { toast } from 'react-toastify';
import Link from 'next/link';
import { Account } from '../../../types/api';

const OVERDRAFT_LIMIT = -1000; // Match the limit from api.ts

export default function WithdrawPage() {
  const params = useParams();
  const [currentUser] = useAtom(currentUserAtom);
  const [account, setAccount] = useState<Account | null>(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const router = useRouter();
  
  const accountId = params?.id ? parseInt(params.id as string) : 0;

  // We'll create the schema after fetching the account to use the account balance in validation
  const [withdrawSchema, setWithdrawSchema] = useState<z.ZodType<any> | null>(null);

  useEffect(() => {
    if (!currentUser) {
      router.push('/login');
      return;
    }

    if (!accountId) {
      toast.error('Invalid account ID');
      router.push('/dashboard');
      return;
    }

    const fetchAccount = async () => {
      try {
        setLoading(true);
        const accountData = await accountService.getAccount(accountId);
        
        if (!accountData) {
          toast.error('Account not found');
          router.push('/dashboard');
          return;
        }
        
        // Check if account belongs to current user
        if (accountData.user_id !== currentUser.id) {
          toast.error("You don't have access to this account");
          router.push('/dashboard');
          return;
        }
        
        setAccount(accountData);
        
        // Create schema with the account balance for validation
        const maxWithdrawal = accountData.account_type === 'Current' 
          ? accountData.balance - OVERDRAFT_LIMIT // Allow withdrawing up to overdraft limit
          : accountData.balance; // For savings, only allow up to current balance

        setWithdrawSchema(z.object({
          amount: z.coerce
            .number()
            .min(1, 'Withdrawal amount must be at least $1')
            .max(
              maxWithdrawal,
              accountData.account_type === 'Current'
                ? `Withdrawal amount can't exceed ${maxWithdrawal.toFixed(2)} (including overdraft)`
                : `Withdrawal amount can't exceed current balance of $${accountData.balance.toFixed(2)}`
            )
            .nonnegative('Withdrawal amount must be a positive number'),
        }));
      } catch (error) {
        console.error('Error fetching account:', error);
        toast.error('Failed to load account information. Please try again.');
        router.push('/dashboard');
      } finally {
        setLoading(false);
      }
    };

    fetchAccount();
  }, [accountId, currentUser, router]);

  const { register, handleSubmit, formState: { errors } } = useForm<{ amount: number }>({
    resolver: withdrawSchema ? zodResolver(withdrawSchema) : undefined,
    defaultValues: {
      amount: 0,
    },
  });

  const onSubmit = async (data: { amount: number }) => {
    try {
      setSubmitting(true);
      await accountService.withdraw(accountId, { amount: data.amount });
      toast.success(`Successfully withdrew $${data.amount.toFixed(2)}`);
      router.push(`/accounts/${accountId}`);
    } catch (error) {
      let errorMessage = 'Failed to withdraw funds. Please try again.';
      if (error instanceof Error) {
        errorMessage = error.message;
      }
      toast.error(errorMessage);
      console.error('Withdrawal error:', error);
    } finally {
      setSubmitting(false);
    }
  };

  if (!currentUser || loading || !withdrawSchema) {
    return (
      <div className="text-center py-8">
        <p>Loading account information...</p>
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

  const maxWithdrawal = account.account_type === 'Current' 
    ? account.balance - OVERDRAFT_LIMIT 
    : account.balance;

  return (
    <div className="max-w-md mx-auto mt-8">
      <Link 
        href={`/accounts/${accountId}`} 
        className="text-indigo-600 hover:underline mb-4 inline-block"
      >
        ← Back to Account
      </Link>
      
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center mb-6 text-indigo-700">Withdraw Funds</h1>
        
        <div className="mb-6">
          <h2 className="text-sm text-gray-500 mb-1">Current Balance</h2>
          <p className="text-2xl font-bold text-indigo-700">${account.balance.toFixed(2)}</p>
          
          {account.account_type === 'Current' && (
            <div className="mt-2 text-sm">
              <p className="text-gray-600">
                Overdraft available: ${Math.max(0, account.balance - OVERDRAFT_LIMIT).toFixed(2)}
              </p>
              <p className="text-gray-500">
                (Up to ${Math.abs(OVERDRAFT_LIMIT).toFixed(2)} overdraft limit)
              </p>
            </div>
          )}
        </div>
        
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label htmlFor="amount" className="block text-sm font-medium text-gray-700 mb-1">
              Withdrawal Amount
            </label>
            <div className="relative">
              <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">$</span>
              <input
                id="amount"
                type="number"
                step="0.01"
                min="1"
                max={maxWithdrawal}
                {...register('amount')}
                className="w-full pl-8 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
                disabled={submitting}
              />
            </div>
            {errors.amount && (
              <p className="mt-1 text-sm text-red-600">{errors.amount.message}</p>
            )}
          </div>
          
          <button
            type="submit"
            disabled={submitting || maxWithdrawal <= 0}
            className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition disabled:bg-indigo-300"
          >
            {submitting ? 'Processing...' : 'Withdraw Funds'}
          </button>
          
          {maxWithdrawal <= 0 && (
            <p className="text-center text-red-500 text-sm mt-2">
              {account.account_type === 'Current'
                ? 'You have reached your overdraft limit.'
                : 'You have insufficient funds to make a withdrawal.'}
            </p>
          )}
        </form>
      </div>
    </div>
  );
} 