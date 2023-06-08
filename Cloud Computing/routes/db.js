// db.js

const mysql = require('mysql');

// Konfigurasi koneksi ke database MySQL
const connection = mysql.createConnection({
  host: '34.101.53.246',
  port: '3306',
  user: 'root',
  password: '3245',
  database: 'wismata-app',
});

connection.connect((err) => {
  if (err) {
    console.error('Error connecting to MySQL database:', err);
  } else {
    console.log('Connected to MySQL database');
  }
});

module.exports = connection;
