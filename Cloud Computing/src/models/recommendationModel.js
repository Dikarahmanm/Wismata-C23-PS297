const axios = require('axios');
const dbPool = require('../config/database');

const getRecommendation = async () => {
  try {
    const response = await axios.get('https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/recommendation');
    const idWisataData = response.data.IdWisata;
    const query = `SELECT idWisata, namaWisata, fotoWisata FROM wisata WHERE idWisata IN (${idWisataData}) ORDER BY FIELD(idWisata, ${idWisataData})`;
    const [rows] = await dbPool.execute(query);
    return rows;
  } catch (error) {
    console.error('Error:', error.message);
    throw error;
  }
};

module.exports = {
  getRecommendation,
};
