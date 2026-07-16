const express = require("express");

const router = express.Router();

const userController =
    require("../controllers/userController");

const {
    authenticate
} = require("../middleware/authMiddleware");

const {
    authorizeRole
} = require("../middleware/roleMiddleware");

router.get(
    "/students",
    authenticate,
    authorizeRole("CR"),
    userController.getStudents
);

module.exports = router;