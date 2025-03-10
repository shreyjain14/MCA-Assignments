'use client';

import { Provider } from 'jotai';
import { ReactNode, useEffect } from 'react';
import { useAtom } from 'jotai';
import { currentUserAtom } from './store/atoms';
import { authUtils } from './utils/auth';

function AuthInitializer({ children }: { children: ReactNode }) {
  const [currentUser, setCurrentUser] = useAtom(currentUserAtom);

  useEffect(() => {
    // Load user from localStorage when the app initializes
    if (!currentUser) {
      const savedUser = authUtils.getUser();
      if (savedUser) {
        console.log('Auto-login from localStorage:', savedUser);
        setCurrentUser(savedUser);
      }
    }
  }, [currentUser, setCurrentUser]);

  return <>{children}</>;
}

export function AppProvider({ children }: { children: ReactNode }) {
  return (
    <Provider>
      <AuthInitializer>
        {children}
      </AuthInitializer>
    </Provider>
  );
} 