'use client';

import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { toast } from 'react-toastify';
import { useRouter } from 'next/navigation';
import { accountService } from '../../services/api';
import { useAtom } from 'jotai';
import { currentUserAtom } from '../../store/atoms';

const accountSchema = z.object({
  account_type: z.enum(['Savings', 'Current'], {
    errorMap: () => ({ message: 'Please select an account type' }),
  }),
  initial_balance: z.coerce
    .number()
    .min(100, 'Initial balance must be at least $100')
    .nonnegative('Initial balance must be a positive number'),
});

type AccountFormValues = z.infer<typeof accountSchema>;

export default function CreateAccount() {
  const [loading, setLoading] = useState(false);
  const router = useRouter();
  const [currentUser] = useAtom(currentUserAtom);
  
  const { register, handleSubmit, formState: { errors } } = useForm<AccountFormValues>({
    resolver: zodResolver(accountSchema),
    defaultValues: {
      account_type: 'Savings',
      initial_balance: 100,
    },
  });

  if (!currentUser) {
    // Redirect to login if not authenticated
    router.push('/login');
    return null;
  }

  const onSubmit = async (data: AccountFormValues) => {
    try {
      setLoading(true);
      
      // Add user_id to the request
      const requestData = {
        ...data,
        user_id: currentUser.id,
      };
      
      await accountService.createAccount(requestData);
      toast.success('Account created successfully!');
      router.push('/dashboard');
    } catch (error) {
      toast.error('Failed to create account. Please try again.');
      console.error('Account creation error:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold text-center mb-6 text-indigo-700">Create New Account</h1>
      
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label htmlFor="account_type" className="block text-sm font-medium text-gray-700 mb-1">
            Account Type
          </label>
          <select
            id="account_type"
            {...register('account_type')}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
            disabled={loading}
          >
            <option value="Savings">Savings</option>
            <option value="Current">Current</option>
          </select>
          {errors.account_type && (
            <p className="mt-1 text-sm text-red-600">{errors.account_type.message}</p>
          )}
        </div>
        
        <div>
          <label htmlFor="initial_balance" className="block text-sm font-medium text-gray-700 mb-1">
            Initial Balance (minimum $100)
          </label>
          <div className="relative">
            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-gray-500">$</span>
            <input
              id="initial_balance"
              type="number"
              step="0.01"
              min="100"
              {...register('initial_balance')}
              className="w-full pl-8 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
              disabled={loading}
            />
          </div>
          {errors.initial_balance && (
            <p className="mt-1 text-sm text-red-600">{errors.initial_balance.message}</p>
          )}
        </div>
        
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition disabled:bg-indigo-300"
        >
          {loading ? 'Creating Account...' : 'Create Account'}
        </button>
      </form>
    </div>
  );
} 