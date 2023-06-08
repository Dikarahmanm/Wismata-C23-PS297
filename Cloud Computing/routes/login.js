// login.js

const express = require('express');
const router = express.Router();
const db = require('./db');

router.post('/', (req, res) => {
  const { username, password } = req.body;

  // Periksa apakah username dan password telah diberikan
  if (!username || !password) {
    res.status(400).json({ error: 'Username and password are required' });
    return;
  }

  // Query untuk memeriksa apakah ada pengguna dengan username dan password yang cocok
  const query = 'SELECT * FROM users WHERE username = ? AND password = ?';
  db.query(query, [username, password], (error, results) => {
    if (error) {
      console.error('Error executing SQL query:', error);
      res.status(500).json({ error: 'Internal server error' });
      return;
    }

    // Periksa apakah ada hasil query yang cocok
    if (results.length === 1) {
      res.json({ message: 'Login successful' });
    } else {
      res.status(401).json({ error: 'Invalid username or password' });
    }
  });
});

module.exports = router;
