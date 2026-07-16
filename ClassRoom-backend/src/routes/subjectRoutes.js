const express = require("express");
const router = express.Router();

const subjectController = require("../controllers/subjectController");
const { authenticate } = require("../middleware/authMiddleware");
const { authorizeRole } = require("../middleware/roleMiddleware");

router.post(
    "/",
    authenticate,
    authorizeRole("CR"),
    subjectController.createSubject
);

router.get(
    "/",
    authenticate,
    subjectController.getSubjects
);

module.exports = router;