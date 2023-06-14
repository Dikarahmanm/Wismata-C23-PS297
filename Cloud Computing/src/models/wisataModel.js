const dbPool = require('../config/database');

const getAllWisata = () => {
  const SQLQuery = 'SELECT * FROM wisata';
  return dbPool.execute(SQLQuery);
};

const Wisata = {};

Wisata.findById = (idWisata) => {
  return db.query('SELECT * FROM wisata WHERE idWisata = ?', [idWisata]);
};

module.exports = {
  getAllWisata,
  Wisata
};
