const express = require("express");
const router = express.Router();

const announcementController = require("../controllers/announcementController");

const { authenticate } = require("../middleware/authMiddleware");
const { authorizeRole } = require("../middleware/roleMiddleware");

router.post(
    "/",
    authenticate,
    authorizeRole("CR"),
    announcementController.createAnnouncement
);

router.get(
    "/",
    authenticate,
    announcementController.getAnnouncements
);

router.put(
    "/:id",
    authenticate,
    authorizeRole("CR"),
    announcementController.updateAnnouncement
);

router.delete(
    "/:id",
    authenticate,
    authorizeRole("CR"),
    announcementController.deleteAnnouncement
);

module.exports = router;