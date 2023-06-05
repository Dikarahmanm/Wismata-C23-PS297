// db.js

const mysql = require('mysql');

// Konfigurasi koneksi ke database MySQL
const connection = mysql.createConnection({
  host: 'alamat_ip_atau_host',
  port: 'port_mysql',
  user: 'username',
  password: 'password',
  database: 'nama_database',
});

connection.connect((err) => {
  if (err) {
    console.error('Error connecting to MySQL database:', err);
  } else {
    console.log('Connected to MySQL database');
  }
});

module.exports = connection;
