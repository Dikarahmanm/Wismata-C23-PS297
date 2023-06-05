const logRequest = (req, res, next) => {
    console.log('Successfully Handled Request API to PATH: ', req.path);
    next();
  }

  module.exports = logRequest;

