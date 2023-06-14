const express = require('express');
const router = express.Router();
const wisataController = require('../controllers/wisataController');

router.get('/AllWisata', wisataController.getAllWisata);

router.get('/:idWisata', wisataController.getWisata);

module.exports = router;
