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

const loginUser = async (req, body) => {
    const connection = await dbPool.getConnection();
    await connection.beginTransaction();

    const query = 'SELECT * FROM user WHERE email = ?';
    db.query(query, [email], async (error, results) => {
      if (error) {
        console.error('Error executing SQL query:', error);
        res.status(500).json({ error: 'Internal server error' });
        return;
      }
    
      if (results.length === 1) {
        const user = results[0];
        const isPasswordMatch = await bcrypt.compare(password, user.password);
    
        if (isPasswordMatch) {
          res.json({ message: 'Login successful' });
        } else {
          res.status(401).json({ error: 'Invalid username or password' });
        }
      } else {
        res.status(401).json({ error: 'Invalid username or password' });
      }
    });
  }

  const getUserByEmail = (email) => {
    const SQLQuery = `SELECT * FROM user WHERE email='${email}'`;
  
    return dbPool.execute(SQLQuery);
  };

  const getAllWisata = () => {
    const SQLQuery = 'SELECT * FROM wisata';
    return dbPool.execute(SQLQuery);
  };

module.exports = {
    getAllUsers,
    getUser,
    createNewUser,
    updateUser,
    deleteUser,
    loginUser,
    getUserByEmail,
    getAllWisata,
}