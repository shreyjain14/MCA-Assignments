'use client';

import { useAtom } from 'jotai';
import Link from 'next/link';
import { currentUserAtom } from '../store/atoms';
import { authUtils } from '../utils/auth';
import { useRouter } from 'next/navigation';

export default function Navigation() {
  const [currentUser, setCurrentUser] = useAtom(currentUserAtom);
  const router = useRouter();

  const handleLogout = () => {
    // Clear user from localStorage
    authUtils.clearUser();
    
    // Clear user from global state
    setCurrentUser(null);
    
    // Redirect to home
    router.push('/');
  };

  return (
    <nav className="bg-indigo-600 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <Link href="/" className="text-xl font-bold">
          Banking App
        </Link>
        <div className="flex gap-4">
          {currentUser ? (
            <>
              <Link href="/dashboard" className="hover:text-indigo-200">
                Dashboard
              </Link>
              <Link href="/accounts" className="hover:text-indigo-200">
                Accounts
              </Link>
              <button 
                className="hover:text-indigo-200"
                onClick={handleLogout}
              >
                Logout
              </button>
            </>
          ) : (
            <>
              <Link href="/register" className="hover:text-indigo-200">
                Register
              </Link>
              <Link href="/login" className="hover:text-indigo-200">
                Login
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
} 