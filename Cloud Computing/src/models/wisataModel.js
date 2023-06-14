const dbPool = require('../config/database');

const getAllWisata = () => {
  const SQLQuery = 'SELECT * FROM wisata';
  return dbPool.execute(SQLQuery);
};


const updateWisata = (body, idWisata, res) => {
  if (!body.fotoWisata) {
    return res.status(400).json({ message: 'No file uploaded' });
  }

  const folderPath = 'fotoWisata';
  const fileName = `${folderPath}/${body.fotoWisata.originalname}`;

  const file = wismataBucket.file(fileName);

  const blobStream = file.createWriteStream({
    resumable: false,
    gzip: true
  });

  blobStream.on('error', (error) => {
    console.error(error);
    return res.status(500).json({ message: 'Failed to upload file' });
  });

  blobStream.on('finish', async () => {
    const fileLocation = `https://storage.googleapis.com/${wismataBucket.name}/${folderPath}/${file.name}`;
    try {
      // Save the file location to the database
      const SQLQuery = `UPDATE wisata SET 
        namaWisata='${body.namaWisata}', 
        deskripsiWisata='${body.deskripsiWisata}', 
        kategoriWisata='${body.kategoriWisata}', 
        kota='${body.kota}', 
        harga='${body.harga}', 
        rating='${body.rating}',
        fotoWisata='${fileLocation}'     
      WHERE idWisata=${idWisata}`;

      await dbPool.execute(SQLQuery);

      return res.status(200).json({ message: 'File uploaded successfully' });
    } catch (error) {
      console.error(error);
      return res.status(500).json({ message: 'Failed to save file location to the database' });
    }

  })};




module.exports = {
  getAllWisata,
  updateWisata
};
