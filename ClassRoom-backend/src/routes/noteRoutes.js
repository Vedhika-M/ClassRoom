const express = require("express");
const router = express.Router();

const upload = require("../config/multer");
const noteController = require("../controllers/noteController");
const { authenticate } = require("../middleware/authMiddleware");

router.post(
    "/:subjectId",
    authenticate,
    upload.single("file"),
    noteController.uploadNote
);

router.get(
    "/:subjectId",
    authenticate,
    noteController.getNotes
);

module.exports = router;