const dbPool = require('../config/database');
const axios = require('axios');

const getAllWisata = () => {
  const SQLQuery = 'SELECT * FROM wisata';
  return dbPool.execute(SQLQuery);
};

const Wisata = {};


Wisata.findById = (idWisata) => {
  return db.query('SELECT * FROM wisata WHERE idWisata = ?', [idWisata]);
};


/**
 * 
 * Fungsi untuk mendapatkan rekomendasi wisata berdasarkan idUser yg telah direkomendasikan oleh model machine learning sesuai dengan data yang telah di rating oleh user
 * @param {number} idUser - idUser yang akan direkomendasikan
 */

const getRecommendation = async (idUser) => {

    const getData = await axios.get('https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/recommendation' + "?IdUser=" + idUser)

    // This get data from machine learning model is show the result in json format, So I want to retrive IdWisata from json format

    const SQLQuery = `SELECT * FROM wisata WHERE IdWisata IN (${getData.data.map((item) => item.IdWisata)}) ORDER BY FIELD(IdWisata, ${getData.data.map((item) => item.IdWisata)}) `;



    return dbPool.execute(SQLQuery);

}


/**
 * 
 * Fungsi untuk menebak rating wisata berdasarkan category dan price range yang dipilih oleh user
 */

const getRating = async (category, priceRange) => {

    const getData = await axios.get('https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/rating' + "?category=" + category + "&priceRange=" + priceRange)

    // Menampilkan hasil rating dari machine learning model dan tempat wisata yang sesuai dengan rating dari model machine learning




}




module.exports = {
  getAllWisata,
  Wisata,
  getRecommendation,
};
