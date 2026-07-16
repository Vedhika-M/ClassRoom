const attendanceService = require("../services/attendanceService");

const markAttendance = async (req, res) => {
    try {
        const session = await attendanceService.markAttendance(
            req.params.subjectId,
            req.body.date,
            req.body.attendance
        );
        res.status(201).json(session);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const editAttendance = async (req, res) => {
    try {
        const result = await attendanceService.editAttendance(
            req.params.sessionId,
            req.body.attendance
        );
        res.json(result);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getAttendanceHistory = async (req, res) => {
    try {
        const history = await attendanceService.getAttendanceHistory(
            req.params.subjectId,
            req.user.id
        );
        res.json(history);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getAttendanceSummary = async (req, res) => {
    try {
        const summary = await attendanceService.getAttendanceSummary(
            req.params.subjectId,
            req.user.id
        );
        res.json(summary);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    markAttendance,
    editAttendance,
    getAttendanceHistory,
    getAttendanceSummary
};