const express = require('express');

const UserController = require('../controller/usersControlller.js');

const router = express.Router();

// CREATE - POST
router.post('/', UserController.createNewUser);

// READ - GET
router.get('/', UserController.getAllUsers);

// READ - GET
router.post('/profile', UserController.getUser);

// UPDATE - PATCH
router.patch('/:idUser', UserController.updateUser);

// DELETE - DELETE
router.delete('/:idUser', UserController.deleteUser);

// LOGIN - LOGIN
router.post('/login', UserController.loginUser);


module.exports = router;