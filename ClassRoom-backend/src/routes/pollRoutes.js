const express = require("express");
const router = express.Router();

const pollController = require("../controllers/pollController");
const { authenticate } = require("../middleware/authMiddleware");
const { authorizeRole } = require("../middleware/roleMiddleware");

router.post(
    "/",
    authenticate,
    authorizeRole("CR"),
    pollController.createPoll
);

router.get(
    "/",
    authenticate,
    pollController.getPolls
);

router.post(
    "/:id/vote",
    authenticate,
    pollController.votePoll
);

module.exports = router;