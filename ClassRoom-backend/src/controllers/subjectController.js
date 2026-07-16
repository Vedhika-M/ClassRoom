const subjectService = require("../services/subjectService");

const createSubject = async (req, res) => {
    try {
        const { name, code } = req.body;

        const subject = await subjectService.createSubject(
            name,
            code
        );

        res.status(201).json({
            message: "Subject created successfully",
            subject
        });
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getSubjects = async (req, res) => {
    try {
        const subjects = await subjectService.getSubjects();

        res.json(subjects);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    createSubject,
    getSubjects
};