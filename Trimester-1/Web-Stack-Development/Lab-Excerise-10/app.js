const mysql = require('mysql2');
const dotenv = require('dotenv');

// Load environment variables
dotenv.config();

// Create a connection to the database
const connection = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME
});

// Connect to the database
connection.connect((err) => {
  if (err) {
    console.error('Error connecting to the database: ' + err.stack);
    return;
  }
  console.log('Connected to database.');

  // Perform a sample query
  connection.query('SELECT * FROM users LIMIT 5', (error, results, fields) => {
    if (error) throw error;
    console.log('The first 5 users are: ', results);

    // Close the connection
    connection.end((err) => {
      if (err) {
        console.error('Error closing the database connection: ' + err.stack);
        return;
      }
      console.log('Database connection closed.');
    });
  });
});