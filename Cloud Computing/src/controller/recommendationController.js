const RecommendationModel = require('../models/recommendationModel');

const getRecommendation = async (req, res) => {
  try {

    User_Id = 1;
    
    const recommendation = await RecommendationModel.getRecommendation(User_Id);
    res.json({
      message: 'GET recommendation success',
      data: recommendation,
    });
  } catch (error) {
    console.error('Error getting recommendation:', error);
    res.status(500).json({ error: 'Internal server error' });
  }
};

module.exports = {
  getRecommendation,
};
