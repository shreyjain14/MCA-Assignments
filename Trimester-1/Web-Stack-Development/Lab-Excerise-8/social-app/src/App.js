import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';
import Navbar from './components/Navbar';
import Post from './components/Post';
import Login from './components/Login';
import Register from './components/Register';
import PostForm from './components/PostForm';
import UserPage from './components/UserPage';

const App = () => {
  const [user, setUser] = useState(null);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const accessToken = Cookies.get('accessToken');
    if (accessToken) {
      setUser('CurrentUser');
      fetchPosts(accessToken);
    }
  }, []);

  const fetchPosts = async (accessToken) => {
    try {
      const response = await axios.get('/api/thoughts/get', {
        headers: { Authorization: `Bearer ${accessToken}` }
      });
      setPosts(response.data);
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
  };

  const handlePostCreated = (newPost) => {
    setPosts([newPost, ...posts]);
  };

  const Home = () => (
    <div className="container mx-auto p-4 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold mb-4 text-center">ThoughtShare</h1>
      <p className="text-center mb-4">Welcome, {user}!</p>
      <PostForm onPostCreated={handlePostCreated} />
      <div className="flex flex-col items-center">
        {posts.map(post => (
          <Post key={post.id} {...post} />
        ))}
      </div>
    </div>
  );

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/login" element={<Login setUser={setUser} />} />
        <Route path="/register" element={<Register />} />
        <Route path="/user/:username" element={<UserPage />} />
        <Route 
          path="/" 
          element={user ? <Home /> : <Navigate to="/login" replace />} 
        />
      </Routes>
    </Router>
  );
};

export default App;
