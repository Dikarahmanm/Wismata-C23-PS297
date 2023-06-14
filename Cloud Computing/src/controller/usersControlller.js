const UsersModel = require('../models/usersModel');
const bcrypt = require('bcrypt');
const jwt = require ('jsonwebtoken')
const getAllUsers = async (req, res) => {
    try {
        const [data] = await UsersModel.getAllUsers();
    
        res.json({
            message: 'GET all users success',
            data: data
        })
    } catch (error) {
        res.status(500).json({
            message: 'Server Error',
            serverMessage: error,
        })
    }
}

const getUser = async (req, res) => {
    const {idUser} = req.params;
    try {
        const [data] = await UsersModel.getUser(idUser);
    
        res.json({
            message: 'GET user success',
            data: data
        })
    } catch (error) {
        res.status(500).json({
            message: 'Server Error',
            serverMessage: error,
        })
    }
}

const createNewUser = async (req, res) => {
    const {body} = req;

    if(!body.email || !body.password){
        return res.status(400).json({
            message: 'Invalid Data',
            data: null,
        })
    }

    try {
        await UsersModel.createNewUser(body);
        res.status(201).json({
            message: 'Register success',
            data: body
        })
    } catch (error) {
        res.status(500).json({
            message: 'Server Error',
            serverMessage: error,
        })
    }
}

const updateUser = async (req, res) => {
    const {idUser} = req.params;
    const {body} = req;
    try {
        await UsersModel.updateUser(body, idUser);
        res.json({
            message: 'UPDATE user success',
            data: {
                id: idUser,
                ...body
            },
        })
    } catch (error) {
        res.status(500).json({
            message: 'Server Error',
            serverMessage: error,
        })
    }
}

const deleteUser = async (req, res) => {
    const {idUser} = req.params;
    try {
        await UsersModel.deleteUser(idUser);
        res.json({
            message: 'DELETE user success',
            data: null
        })
    } catch (error) {
        res.status(500).json({
            message: 'Server Error',
            serverMessage: error,
        })
    }
}

const loginUser = async (req, res) => {
    const { body } = req;
try {
    const user = await UsersModel.getUserbyEmail(body);
    const userId = user[0][0].id;
    const email = user[0][0].email;
    const hashedPassword = user[0][0].password;
    const match = await bcrypt.compare(body.password, hashedPassword);
    if(!match) return res.status(400).json({message: "Invalid Email or Password"});
    const accessToken = jwt.sign({userId, email}, process.env.ACCESS_TOKEN_SECRET, {
        expiresIn: '20s'
    });
    const refreshToken = jwt.sign({userId, email}, process.env.REFRESH_TOKEN_SECRET, {
        expiresIn: '2d'
    });
    const auth = await UsersModel.loginUser(refreshToken, userId);
    res.cookie('refreshToken', refreshToken, {
    httpOnly: true,
    maxAge: 48 * 60 * 60 * 1000,
    });
    res.json({ accessToken });
}catch (error){
res.status(404).json({msg:"Email Not Found"})
}
}
  
  

module.exports = {
    getAllUsers,
    getUser,
    createNewUser,
    updateUser,
    deleteUser,
    loginUser,
}