import React, { useState } from 'react';
import { FaHeart } from 'react-icons/fa';

const Card = ({ title, features, image }) => {
  const [isLiked, setIsLiked] = useState(false);

  const toggleLike = () => {
    setIsLiked(!isLiked);
  };

  return (
    <div className="max-w-sm rounded overflow-hidden shadow-lg m-4">
      <img className="w-full" src={image} alt={title} />
      <div className="px-6 py-4">
        <div className="font-bold text-xl mb-2">{title}</div>
        <ul className="text-gray-700 text-base">
          {features.map((feature, index) => (
            <li key={index}>{feature}</li>
          ))}
        </ul>
      </div>
      <div className="px-6 pt-4 pb-2">
        <button onClick={toggleLike} className="focus:outline-none">
          <FaHeart className={`transition-all duration-300 ${isLiked ? 'text-red-500 scale-125' : 'text-gray-500'}`} />
        </button>
      </div>
    </div>
  );
};

export default Card;
