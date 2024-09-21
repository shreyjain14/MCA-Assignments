import React, { useState } from 'react';
import Cookies from 'js-cookie';

const PostForm = ({ onPostCreated }) => {
  const [newPost, setNewPost] = useState('');
  const [isAnonymous, setIsAnonymous] = useState(false);

  const handlePostSubmit = async (e) => {
    e.preventDefault();
    if (newPost.trim() !== '') {
      try {
        const accessToken = Cookies.get('accessToken');
        const response = await fetch('/api/thoughts/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${accessToken}`
          },
          body: JSON.stringify({
            content: newPost,
            anonymous: isAnonymous
          })
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const createdPost = await response.json();
        onPostCreated(createdPost);
        setNewPost('');
        setIsAnonymous(false);
      } catch (error) {
        console.error('Error creating post:', error);
      }
    }
  };

  return (
    <form onSubmit={handlePostSubmit} className="mb-8">
      <textarea
        className="w-full p-2 rounded-lg border-2 border-gray-300 focus:outline-none focus:border-blue-500"
        rows="3"
        placeholder="Share your thoughts..."
        value={newPost}
        onChange={(e) => setNewPost(e.target.value)}
      ></textarea>
      <div className="mt-2">
        <label className="inline-flex items-center">
          <input
            type="checkbox"
            className="form-checkbox"
            checked={isAnonymous}
            onChange={(e) => setIsAnonymous(e.target.checked)}
          />
          <span className="ml-2">Post anonymously</span>
        </label>
      </div>
      <button 
        type="submit"
        className="mt-2 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none"
      >
        Post
      </button>
    </form>
  );
};

export default PostForm;
