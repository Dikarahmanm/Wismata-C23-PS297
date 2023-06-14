require('dotenv').config()
const PORT = process.env.PORT || 5000;
const express = require('express');
const session = require('express-session');
const usersRoutes = require('./routes/usersRoutes');
const authRoutes = require('./routes/authRoutes');
const wisataRoutes = require('./routes/wisataRoutes');
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
app.use('/login',authRoutes);
app.use('/AllWisata', wisataRoutes);
app.use('/wisata', wisataRoutes);

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


app.listen(PORT, () => {
    console.log(`Server successfully running on ${PORT}`);
})