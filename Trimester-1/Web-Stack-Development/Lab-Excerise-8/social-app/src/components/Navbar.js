import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
  const [search, setSearch] = useState('');
  const navigate = useNavigate();

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    if (search.trim() !== '') {
      navigate(`/user/${search}`);
    }
  };

  return (
    <nav className="bg-blue-600 p-4">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="text-white text-2xl font-bold">ThoughtShare</Link>
        <form onSubmit={handleSearchSubmit} className="flex items-center">
          <input
            type="text"
            placeholder="Search by username"
            className="px-4 py-2 rounded-l-lg border-none focus:outline-none"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <button 
            type="submit"
            className="px-4 py-2 bg-blue-700 text-white rounded-r-lg hover:bg-blue-800 focus:outline-none"
          >
            Search
          </button>
        </form>
      </div>
    </nav>
  );
};

export default Navbar;
