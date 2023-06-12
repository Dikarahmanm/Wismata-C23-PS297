require('dotenv').config()
const PORT = process.env.PORT || 5000;
const express = require('express');
const session = require('express-session');
const usersRoutes = require('./routes/users');
const path = require('path');
const middlewareLogRequest = require('./middleware/logs');
const upload = require('./middleware/multer');

const app = express();

app.use(middlewareLogRequest);
app.use(express.json());
app.use('/assets', express.static('public/images'))
app.use(session({
	secret: process.env.ACCESS_TOKEN_SECRET,
	resave: true,
	saveUninitialized: true
}));
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'static')));
app.use('/users', usersRoutes);
app.use('/auth', usersRoutes);

app.get('/', (req, res) => {
    res.send('Route default');
  });

app.post('/upload',upload.single('photo'),(req, res) => {
    res.json({
        message: 'Upload success'
    })
})

app.use((err, req, res, next) => {
    res.json({
        message: err.message
    })
})

// Import getAllWisata dari usersController
const { getAllWisata } = require('./src/controller/users');

// Route endpoint untuk getAllWisata
app.get('/AllWisata', getAllWisata);

// Import getWisata dari usersController
const { getWisata } = require('./src/controller/users');

// Route endpoint untuk getWisata
app.get('/wisata/:idWisata', getWisata);

app.listen(PORT, () => {
    console.log(`Server successfully running on ${PORT}`);
})