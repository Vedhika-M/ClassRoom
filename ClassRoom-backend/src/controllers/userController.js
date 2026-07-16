const userService = require("../services/userService");

const getStudents = async (req, res) => {
    try {
        const students =
            await userService.getStudents();

        res.json(students);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    getStudents
};