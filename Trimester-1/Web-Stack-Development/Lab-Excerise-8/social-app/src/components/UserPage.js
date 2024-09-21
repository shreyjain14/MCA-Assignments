import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Post from './Post';

const UserPage = () => {
  const { username } = useParams();
  const [posts, setPosts] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUserPosts = async () => {
      try {
        const response = await axios.get(`/api/thoughts/get/${username}`);
        setPosts(response.data);
      } catch (error) {
        setError('Error fetching user posts');
      }
    };

    fetchUserPosts();
  }, [username]);

  return (
    <div className="container mx-auto p-4 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold mb-4 text-center">Posts by {username}</h1>
      {error && <p className="text-red-500 text-center">{error}</p>}
      <div className="flex flex-col items-center">
        {posts.map(post => (
          <Post key={post.id} {...post} />
        ))}
      </div>
    </div>
  );
};

export default UserPage;
