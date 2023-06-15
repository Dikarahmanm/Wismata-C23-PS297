const axios = require('axios');
const dbPool = require('../config/database');

const getRecommendation = async (UserId) => {
  try {

    // UserId = 2;
    const response = await axios.get(`https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/recommendation?IdUser_Login=${UserId}`);
 
    const idWisataData = response.data.IdWisata;
    const query = `SELECT idWisata, rating, namaWisata, fotoWisata FROM wisata WHERE idWisata IN (${idWisataData}) ORDER BY FIELD(idWisata, ${idWisataData})`;
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
