const express = require('express');
const router = express.Router();
const wisataController = require('../controller/wisataController');

router.get('/', wisataController.getAllWisata);

router.get('/:idWisata', wisataController.getWisata);

module.exports = router;
