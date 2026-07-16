const express = require("express");
const router = express.Router();
const attendanceController = require("../controllers/attendanceController");

const { authenticate } = require("../middleware/authMiddleware");
const { authorizeRole } = require("../middleware/roleMiddleware");

router.post(
    "/:subjectId",
    authenticate,
    authorizeRole("CR"),
    attendanceController.markAttendance
);

router.put(
    "/:sessionId",
    authenticate,
    authorizeRole("CR"),
    attendanceController.editAttendance
);

router.get(
    "/history/:subjectId",
    authenticate,
    attendanceController.getAttendanceHistory
);

router.get(
    "/summary/:subjectId",
    authenticate,
    attendanceController.getAttendanceSummary
);

module.exports = router;