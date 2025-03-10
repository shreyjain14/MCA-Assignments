'use client';

import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { toast } from 'react-toastify';
import { useRouter } from 'next/navigation';
import { useAtom } from 'jotai';
import { currentUserAtom } from '../store/atoms';
import { authUtils } from '../utils/auth';
import Link from 'next/link';

// Note: In a real application, this would be a proper authentication system
// This is a simplified login for demonstration purposes
const loginSchema = z.object({
  userId: z.coerce
    .number()
    .int('User ID must be an integer')
    .positive('User ID must be a positive number'),
});

type LoginFormValues = z.infer<typeof loginSchema>;

export default function Login() {
  const [loading, setLoading] = useState(false);
  const router = useRouter();
  const [, setCurrentUser] = useAtom(currentUserAtom);
  
  const { register, handleSubmit, formState: { errors } } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      userId: undefined,
    },
  });

  const onSubmit = async (data: LoginFormValues) => {
    try {
      setLoading(true);
      
      // Simulate API call to login
      // In a real application, this would call an API endpoint
      setTimeout(() => {
        // Create a mock user for demo purposes
        const user = {
          id: data.userId,
          username: `User${data.userId}`,
          email: `user${data.userId}@example.com`,
        };
        
        // Save user to localStorage for persistence
        authUtils.saveUser(user);
        
        // Update global state
        setCurrentUser(user);
        
        toast.success('Login successful!');
        router.push('/dashboard');
      }, 1000);
    } catch (error) {
      toast.error('Login failed. Please try again.');
      console.error('Login error:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold text-center mb-6 text-indigo-700">Login to Your Account</h1>
      
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label htmlFor="userId" className="block text-sm font-medium text-gray-700 mb-1">
            User ID
          </label>
          <input
            id="userId"
            type="number"
            {...register('userId')}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-indigo-200"
            disabled={loading}
          />
          {errors.userId && (
            <p className="mt-1 text-sm text-red-600">{errors.userId.message}</p>
          )}
        </div>
        
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition disabled:bg-indigo-300"
        >
          {loading ? 'Logging in...' : 'Login'}
        </button>
      </form>
      
      <div className="mt-6 text-center">
        <p className="text-gray-600">Don't have an account?</p>
        <Link href="/register" className="text-indigo-600 hover:underline mt-1 inline-block">
          Register here
        </Link>
      </div>
      
      <div className="mt-8 p-4 bg-gray-50 rounded-md text-sm">
        <h2 className="font-semibold mb-2">Demo Note</h2>
        <p className="text-gray-600 mb-2">
          This is a simplified login for demonstration purposes. In a real application, a proper authentication system would be implemented.
        </p>
        <p className="text-gray-600">
          Enter any positive integer as your User ID to proceed.
        </p>
      </div>
    </div>
  );
} 