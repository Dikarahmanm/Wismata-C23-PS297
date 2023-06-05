const express = require ('express');

const usersRoutes = require ('./routes/users');

const middlewareLogRequest = require('./middleware/logs');

const app = express();

//== middleware ==

app.use(middlewareLogRequest)

//== end of middleware ==

app.use('/users', usersRoutes)


app.get("/", (req, res) => {
  res.json({
    name:"Wellyaz",
    email:"wellyaz.id@gmail.com"
  });
});

app.post("/", (req, res) => {
  res.send('hello POST method');
});

app.listen(4000, () => {
  console.log('Server successfully run on server 4000');
});