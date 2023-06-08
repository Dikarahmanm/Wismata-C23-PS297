
var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

// routing dan impor
var loginRouter = require('./routes/login'); // Impor file login.js
var db = require('./routes/db'); // Impor file db.js
const registerRouter = require('./routes/register'); //Impor file register.js
const searchRouter = require('./routes/search'); //Impor file search

var app = express();

// Endpoint untuk mengambil data pengguna
app.get('/api/users', (req, res) => {
  db.query('SELECT * FROM users', (error, results) => {
    if (error) {
      console.error('Error executing SQL query:', error);
      res.status(500).json({ error: 'Internal server error' });
    } else {
      res.json(results);
    }
  });
});

app.use('/api/login', loginRouter); // Mendaftarkan rute login
app.use('/api/register', registerRouter);// Mendaftarkan rute register
app.use('/api/search', searchRouter);// Mendaftarkan rute search

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
