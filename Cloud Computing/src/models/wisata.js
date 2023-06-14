const dbPool = require('../config/database');
const axios = require('axios');


/**
 * 
 * Fungsi untuk mendapatkan rekomendasi wisata berdasarkan idUser yg telah direkomendasikan oleh model machine learning sesuai dengan data yang telah di rating oleh user
 * @param {number} idUser - idUser yang akan direkomendasikan
 */

const getRecommendation = async (idUser) => {

    const getData = await axios.get('https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/recommendation' + "?IdUser=" + idUser)
    

    const SQLQuery = `SELECT * FROM wisata WHERE IdWisata IN (${getData.data}) ORDER BY FIELD(IdWisata, ${getData.data}) `;

    return getData.data

}


module.exports = {

    getRecommendation

}
