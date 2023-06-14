const multer = require('multer');
const path = require('path');

const upload = multer({
    limits: {
        fileSize: 5 * 1000 * 1000 // 5 MB
    }
});

module.exports = upload;