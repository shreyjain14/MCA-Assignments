import Link from 'next/link';

export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-[80vh]">
      <div className="text-center max-w-4xl mx-auto p-8">
        <h1 className="text-4xl font-bold mb-6 text-indigo-700">Welcome to Modern Banking</h1>
        <p className="text-xl mb-8 text-gray-600">
          Secure, reliable, and easy-to-use banking services at your fingertips.
        </p>
        
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-12">
          <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-3 text-indigo-600">Personal Banking</h2>
            <p className="text-gray-600 mb-4">
              Manage your accounts, make deposits and withdrawals with ease.
            </p>
          </div>
          
          <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-3 text-indigo-600">Savings Accounts</h2>
            <p className="text-gray-600 mb-4">
              Grow your money with our competitive interest rates.
            </p>
          </div>
          
          <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-3 text-indigo-600">Current Accounts</h2>
            <p className="text-gray-600 mb-4">
              Flexible everyday banking for your daily transactions.
            </p>
          </div>
        </div>
        
        <div className="flex flex-col sm:flex-row justify-center gap-4">
          <Link href="/register" className="bg-indigo-600 text-white py-3 px-6 rounded-md hover:bg-indigo-700 transition">
            Create Account
          </Link>
          <Link href="/login" className="bg-white text-indigo-600 border border-indigo-600 py-3 px-6 rounded-md hover:bg-indigo-50 transition">
            Login
          </Link>
        </div>
      </div>
    </div>
  );
}
