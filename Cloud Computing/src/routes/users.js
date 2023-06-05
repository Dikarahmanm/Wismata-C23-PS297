const express = require('express');

const userController = require('../controller/users.js');

const router = express.Router();

//== Get All Function ==

router.get('/', userController.getAllUsers)

router.post('/', userController.createNewUsers)

//== End of Get All Function ==

module.exports = router;