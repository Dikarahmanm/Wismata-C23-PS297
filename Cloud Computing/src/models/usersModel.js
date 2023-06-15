const dbPool = require('../config/database');
const encrypt = require('bcrypt');

const getAllUsers = () => {
    const SQLQuery = 'SELECT * FROM user';

    return dbPool.execute(SQLQuery);
}

const getUser = (idUser) => {
    const SQLQuery = `SELECT * FROM user WHERE id=${idUser}`;

    return dbPool.execute(SQLQuery);
}


const createNewUser = async (body) => {
    const connection = await dbPool.getConnection();
    try {
      await connection.beginTransaction();
  
      const hashedPassword = encrypt.hashSync(body.password, 12);
      const userQuery = `INSERT INTO user (email, password) 
                         VALUES (?, ?)`;
      const userValues = [body.email, hashedPassword];
  
      await connection.query(userQuery, userValues);
  
      const profileQuery = `INSERT INTO profile (userEmail, namaDepan, namaBelakang, bioProfile, fotoProfile, role) 
                            VALUES (?, ?, ?, ?, ?, ?)`;
      const profileValues = [body.email, body.namaDepan, body.namaBelakang, body.bioProfile, body.fotoProfile, body.role];
  
      await connection.query(profileQuery, profileValues);
  
      await connection.commit();
      connection.release();
  
      return Promise.resolve();
    } catch (error) {
      await connection.rollback();
      connection.release();
  
      return Promise.reject(error);
    }
  };
  

const updateUser = (body, idUser) => {
    const hashedPassword = encrypt.hashSync(body.password, 12);
    const SQLQuery = `  UPDATE user 
                        SET email='${body.email}', password='${hashedPassword}' 
                        WHERE id=${idUser}`;

    return dbPool.execute(SQLQuery);
}

const deleteUser = (idUser) => {
    const SQLQuery = `DELETE FROM user WHERE id=${idUser}`;

    return dbPool.execute(SQLQuery);
}

const loginUser = (refreshToken, userId) => {
  const SQLQuery = `  UPDATE user SET token='${refreshToken}' WHERE id=${userId}`;

  return dbPool.execute(SQLQuery);
 };

  const getUserByEmail = (email) => {
    const SQLQuery = 'SELECT * FROM user WHERE email = ?';
    return dbPool.execute(SQLQuery, [email])
      .then(([rows]) => rows[0]);
  };
  


module.exports = {
    getAllUsers,
    getUser,
    createNewUser,
    updateUser,
    deleteUser,
    loginUser,
    getUserByEmail,
}