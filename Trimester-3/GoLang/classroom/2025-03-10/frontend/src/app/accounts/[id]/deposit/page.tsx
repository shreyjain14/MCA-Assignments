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

const depositSchema = z.object({
  amount: z.coerce
    .number()
    .min(1, 'Deposit amount must be at least $1')
    .nonnegative('Deposit amount must be a positive number'),
});

type DepositFormValues = z.infer<typeof depositSchema>;

export default function DepositPage() {
  const params = useParams();
  const [currentUser] = useAtom(currentUserAtom);
  const [account, setAccount] = useState<Account | null>(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const router = useRouter();
  
  const accountId = params?.id ? parseInt(params.id as string) : 0;

  const { register, handleSubmit, formState: { errors } } = useForm<DepositFormValues>({
    resolver: zodResolver(depositSchema),
    defaultValues: {
      amount: 0,
    },
  });

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
        
        // Check if account belongs to current user
        if (accountData.user_id !== currentUser.id) {
          toast.error("You don't have access to this account");
          router.push('/dashboard');
          return;
        }
        
        setAccount(accountData);
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

  const onSubmit = async (data: DepositFormValues) => {
    try {
      setSubmitting(true);
      await accountService.deposit(accountId, { amount: data.amount });
      toast.success(`Successfully deposited $${data.amount.toFixed(2)}`);
      router.push(`/accounts/${accountId}`);
    } catch (error) {
      toast.error('Failed to deposit funds. Please try again.');
      console.error('Deposit error:', error);
    } finally {
      setSubmitting(false);
    }
  };

  if (!currentUser || loading) {
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

  return (
    <div className="max-w-md mx-auto mt-8">
      <Link 
        href={`/accounts/${accountId}`} 
        className="text-indigo-600 hover:underline mb-4 inline-block"
      >
        ← Back to Account
      </Link>
      
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h1 className="text-2xl font-bold text-center mb-6 text-indigo-700">Deposit Funds</h1>
        
        <div className="mb-6">
          <h2 className="text-sm text-gray-500 mb-1">Current Balance</h2>
          <p className="text-2xl font-bold text-indigo-700">${account.balance.toFixed(2)}</p>
        </div>
        
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label htmlFor="amount" className="block text-sm font-medium text-gray-700 mb-1">
              Deposit Amount
            </label>
            <div className="relative">
              <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">$</span>
              <input
                id="amount"
                type="number"
                step="0.01"
                min="1"
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
            disabled={submitting}
            className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition disabled:bg-indigo-300"
          >
            {submitting ? 'Processing...' : 'Deposit Funds'}
          </button>
        </form>
      </div>
    </div>
  );
} 