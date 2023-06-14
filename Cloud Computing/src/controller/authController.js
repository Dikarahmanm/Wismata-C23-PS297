const jwt = require('jsonwebtoken');
const UsersModel = require('../models/usersModel');

const login = async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await UsersModel.getUserByEmail(email);
    if (!user) {
      return res.status(401).json({ message: 'User tidak ditemukan' });
    }

    const isPasswordValid = await UsersModel.verifyPassword(password, user.password);
    if (!isPasswordValid) {
      return res.status(401).json({ message: 'Invalid password' });
    }

    const token = jwt.sign({ id: user.id }, process.env.JWT_SECRET, { expiresIn: '1h' });

    res.json({ token });
  } catch (error) {
    res.status(500).json({ message: 'Server Error', error: error.message });
  }
};

module.exports = {
  login,
};
