const express = require("express");
const router = express.Router();

const examController = require("../controllers/examController");

const { authenticate } = require("../middleware/authMiddleware");
const { authorizeRole } = require("../middleware/roleMiddleware");

router.post(
    "/:subjectId",
    authenticate,
    authorizeRole("CR"),
    examController.createExam
);

router.delete(
    "/:examId",
    authenticate,
    authorizeRole("CR"),
    examController.deleteExam
);

router.get(
    "/",
    authenticate,
    examController.getExams
);

module.exports = router;