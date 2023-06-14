const UsersModel = require('../models/usersModel');
const bcrypt = require('bcrypt');
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
            message: 'CREATE new user success',
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
    const { email, password } = req.body;

    if(req.body.email.trim()===''||req.body.password.trim()===''){
        return res.status(400).send({msg:"email or password must not be empty"})
    
    }
    try {
        const result = await UsersModel.loginUser(email, password);
        res.json({
            message: 'Login user success',
            data: result
        })
    } catch (error) {
        res.status(500).json({
            message: 'Server Error',
            serverMessage: error,
        })
    }
  };
  
  

module.exports = {
    getAllUsers,
    getUser,
    createNewUser,
    updateUser,
    deleteUser,
    loginUser,
}