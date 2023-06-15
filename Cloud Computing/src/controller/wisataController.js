const WisataModel = require('../models/wisataModel');
const Wisata = require('../models/wisataModel');
const db = require('../config/database');

const getAllWisata = async (req, res) => {
  try {
    const [data] = await WisataModel.getAllWisata();
    res.json({
      message: 'GET all wisata success',
      data: data
    });
  } catch (error) {
    res.status(500).json({
      message: 'Server Error',
      serverMessage: error
    });
  }
};

const getWisata = async (req, res) => {
  const { idWisata } = req.params;

  try {
    const wisata = await Wisata.getWisataById(idWisata);
    const umkm = await Wisata.getUMKMByWisataId(idWisata);

    if (!wisata) {
      return res.status(404).json({ error: 'Wisata tidak ditemukan' });
    }

    res.json({ message : 'Success',wisata, umkm });
  } catch (error) {
    console.error('Error getting wisata:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

module.exports = {
  getAllWisata,
  getWisata,
};
