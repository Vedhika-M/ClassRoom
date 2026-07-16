const examService = require("../services/examService");

const createExam = async (req, res) => {
    try {
        const exam = await examService.createExam(
            req.params.subjectId,
            req.body.examType,
            req.body.examDate,
            req.body.examTime,
            req.body.notes
        );

        res.status(201).json(exam);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const deleteExam = async (req, res) => {
    try {
        await examService.deleteExam(req.params.examId);

        res.json({
            message: "Exam deleted successfully"
        });
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getExams = async (req, res) => {
    try {
        const exams = await examService.getExams();

        res.json(exams);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    createExam,
    deleteExam,
    getExams
};