const dbPool = require('../config/database');

const getAllWisata = () => {
  const SQLQuery = 'SELECT * FROM wisata';
  return dbPool.execute(SQLQuery);
};


const updateWisata = (body, idWisata) => {
  const SQLQuery = `  UPDATE user 
                      SET email='${body.email}', password='${hashedPassword}' 
                      WHERE id=${idWisata}`;

  return dbPool.execute(SQLQuery);
}

module.exports = {
  getAllWisata,
  updateWisata
};
