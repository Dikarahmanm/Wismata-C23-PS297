const dbPool = require('../config/database');

const getAllUsers = () => {
    const SQLQuery = 'SELECT * FROM user';

    return dbPool.execute(SQLQuery);
}

const getUser = (idUser) => {
    const SQLQuery = `SELECT * FROM user WHERE id=${idUser}`;

    return dbPool.execute(SQLQuery);
}


const createNewUser = (body) => {
    const SQLQuery = `  INSERT INTO user (email, password) 
                        VALUES ( '${body.email}', '${body.password}')`;

    return dbPool.execute(SQLQuery);
}

const updateUser = (body, idUser) => {
    const SQLQuery = `  UPDATE user 
                        SET email='${body.email}', password='${body.password}' 
                        WHERE id=${idUser}`;

    return dbPool.execute(SQLQuery);
}

const deleteUser = (idUser) => {
    const SQLQuery = `DELETE FROM user WHERE id=${idUser}`;

    return dbPool.execute(SQLQuery);
}

module.exports = {
    getAllUsers,
    getUser,
    createNewUser,
    updateUser,
    deleteUser,
}