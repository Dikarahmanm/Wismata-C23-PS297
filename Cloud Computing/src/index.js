require('dotenv').config()
const PORT = process.env.PORT || 5000;
const express = require('express');
const session = require('express-session');
const usersRoutes = require('./routes/usersRoutes');
const wisataRoutes = require('./routes/wisataRoutes');
const path = require('path');
const middlewareLogRequest = require('./middleware/logs');
const upload = require('./middleware/multer');
const { Storage } = require('@google-cloud/storage');

const app = express();

const gc = new Storage ({
keyFilename: path.join(__dirname, "../phrasal-faculty-387812-98a4a790792f.json"),
projectId: 'phrasal-faculty-387812'
});

const wismataBucket = gc.bucket('wismata-bucket')

app.use(middlewareLogRequest);
app.use(express.json());
app.use('/assets', express.static('public/images'))
app.use("/images", express.static(path.join(__dirname, "../images")));
app.use(session({
	secret: process.env.ACCESS_TOKEN_SECRET,
	resave: true,
	saveUninitialized: true
}));
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'static')));
app.use('/users', usersRoutes);
app.use('/AllWisata', wisataRoutes);
app.use('/wisata', wisataRoutes);

app.get('/', (req, res) => {
    res.send('Route default');
  });


  app.post('/upload', upload.single('image'), (req, res, next) => {
    if (!req.file) {
      return res.status(400).json({ message: 'No file uploaded' });
    }
  
    const file = wismataBucket.file(req.file.originalname);
  
    const blobStream = file.createWriteStream({
      resumable: false,
      gzip: true
    });
  
    blobStream.on('error', (error) => {
      console.error(error);
      return res.status(500).json({ message: 'Failed to upload file' });
    });
  
    blobStream.on('finish', () => {
      return res.status(200).json({ message: 'File uploaded successfully' });
    });
  
    blobStream.end(req.file.buffer);
})

app.use((err, req, res, next) => {
    res.json({
        message: err.message
    })
})


app.listen(PORT, () => {
    console.log(`Server successfully running on ${PORT}`);
})