const express = require('express');
const router = express.Router();
const db = require('./db');

router.post('/', (req, res) => {
  const { username, password, email } = req.body;

  // Periksa apakah semua data yang diperlukan telah diberikan
  if (!username || !password || !email) {
    res.status(400).json({ error: 'Username, password, and email are required' });
    return;
  }

  // Query untuk memasukkan data pengguna baru ke dalam tabel 'users'
  const query = 'INSERT INTO users (username, password, email) VALUES (?, ?, ?)';
  db.query(query, [username, password, email], (error, results) => {
    if (error) {
      console.error('Error executing SQL query:', error);
      res.status(500).json({ error: 'Internal server error' });
      return;
    }

    res.json({ message: 'User registered successfully' });
  });
});

module.exports = router;
