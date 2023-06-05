const getAllUsers = (req, res) => {
    res.json({
        messages : "GET all users success",
    })
}

const createNewUsers = (req, res) => {
    res.json({
        messages : "CREATE new users success",
    })
}

module.exports = {
    getAllUsers,
    createNewUsers
}