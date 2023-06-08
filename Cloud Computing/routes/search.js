const express = require('express');
const router = express.Router();
const db = require('./db');

// Endpoint untuk pencarian pengguna berdasarkan kata kunci
router.get('/', (req, res) => {
  const keyword = req.query.keyword;

  if (!keyword) {
    res.status(400).json({ error: 'Keyword parameter is required' });
    return;
  }

  const query = 'SELECT * FROM users WHERE username LIKE ? OR email LIKE ?';
  const searchKeyword = `%${keyword}%`;
  db.query(query, [searchKeyword, searchKeyword], (error, results) => {
    if (error) {
      console.error('Error executing SQL query:', error);
      res.status(500).json({ error: 'Internal server error' });
      return;
    }

    res.json(results);
  });
});

module.exports = router;
