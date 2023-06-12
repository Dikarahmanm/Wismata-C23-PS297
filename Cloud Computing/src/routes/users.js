const express = require('express');

const UserController = require('../controller/users.js');

const router = express.Router();

// CREATE - POST
router.post('/', UserController.createNewUser);

// READ - GET
router.get('/', UserController.getAllUsers);

// READ - GET
router.get('/:idUser', UserController.getUser);

// UPDATE - PATCH
router.patch('/:idUser', UserController.updateUser);

// DELETE - DELETE
router.delete('/:idUser', UserController.deleteUser);

router.post('/auth', UserController.loginUser);

// Login dengan JWT
router.post('/login', async (req, res) => {
    try {
      const { email, password } = req.body;
  
      // Validasi input
      if (!email || !password) {
        return res.status(400).json({ message: 'Mohon isi semua field' });
      }
    
      // Cek apakah pengguna ada dalam database
      const user = await UserController.getUserByEmail(email);
  
      if (!user) {
        return res.status(401).json({ message: 'Email atau password salah' });
      }
  
      // Periksa kredensial pengguna
      const isPasswordValid = await bcrypt.compare(password, user.password);
  
      if (!isPasswordValid) {
        return res.status(401).json({ message: 'Email atau password salah' });
      }
  
      // Jika kredensial valid, buat token JWT
      const accessToken = jwt.sign({ userId: user._id, email: user.email }, process.env.ACCESS_TOKEN_SECRET);
  
      // Kirim token JWT sebagai respons
      res.json({ accessToken });
    } catch (error) {
      res.status(500).json({ message: 'Terjadi kesalahan server' });
    }
  });

  router.get('/AllWisata', UserController.getAllWisata);

module.exports = router;