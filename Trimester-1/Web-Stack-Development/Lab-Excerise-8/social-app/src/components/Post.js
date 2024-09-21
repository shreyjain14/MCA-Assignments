import React from 'react';
import { Link } from 'react-router-dom';

const Post = ({ content, user_id, date }) => {
  return (
    <div className="max-w-md rounded-lg overflow-hidden shadow-lg m-4 bg-white">
      <div className="px-6 py-4">
        <Link to={`/user/${user_id}`} className="font-bold text-xl mb-2 text-blue-600 hover:underline">
          {user_id || 'Anonymous'}
        </Link>
        <p className="text-gray-700 text-base mb-4">{content}</p>
        <p className="text-gray-500 text-sm">{new Date(date).toLocaleString()}</p>
      </div>
    </div>
  );
};

export default Post;
