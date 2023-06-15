const dbPool = require('../config/database');
const axios = require('axios');

const getAllWisata = () => {
  const SQLQuery = 'SELECT * FROM wisata';
  return dbPool.execute(SQLQuery);
};

const getWisataById = (idWisata) => {
  const SQLQuery = 'SELECT * FROM wisata WHERE idWisata = ?';
  return dbPool.execute(SQLQuery, [idWisata])
    .then(([rows]) => rows[0]);
};

const getUMKMByWisataId = (idWisata) => {
  const SQLQuery = 'SELECT * FROM umkm WHERE idWisata = ?';
  return dbPool.execute(SQLQuery, [idWisata])
    .then(([rows]) => rows);
};


module.exports = {
  getAllWisata,
  getWisataById,
  getUMKMByWisataId,
};
