const express = require('express');
const router = express.Router();
const recommendationController = require('../controller/recommendationController');

router.get('/', recommendationController.getRecommendation);

module.exports = router;
