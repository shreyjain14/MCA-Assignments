import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ImageGallery.css'; 

function ImageGallery() {
  const [drivers, setDrivers] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await axios.get('https://api.openf1.org/v1/drivers');
        const uniqueDrivers = response.data.reduce((acc, driver) => {
          if (!acc.find(d => d.driver_number === driver.driver_number) && driver.headshot_url) {
            acc.push(driver);
          }
          return acc;
        }, []);
        setDrivers(uniqueDrivers);
        setIsLoading(false);
      } catch (error) {
        console.error('Error fetching drivers:', error);
        setIsLoading(false);
      }
    };

    fetchDrivers();
  }, []);

  if (isLoading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p>Loading drivers...</p>
      </div>
    );
  }

  return (
    <div>
      <h2>F1 Driver Gallery</h2>
      <div className="image-container">
        {drivers.map((driver) => (
          <div key={driver.driver_number} className="driver-card">
            <img src={driver.headshot_url} alt={`${driver.full_name}`} />
            <p>{driver.full_name}</p>
            <p>Number: {driver.driver_number}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ImageGallery;
