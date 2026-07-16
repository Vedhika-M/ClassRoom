const announcementService = require("../services/announcementService");

const createAnnouncement = async (req, res) => {
    try {
        const { title, body } = req.body;
        const announcement =
            await announcementService.createAnnouncement(
                title,
                body,
                req.user.id
            );

        res.status(201).json({
            message: "Announcement created successfully",
            announcement
        });
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getAnnouncements = async (req, res) => {
    try {
        const announcements = 
        await announcementService.getAnnouncements();

        res.json(announcements);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const updateAnnouncement = async (req, res) => {
    try {
        const { title, body } = req.body;
        const announcement =
            await announcementService.updateAnnouncement(
                req.params.id,
                title,
                body
            );

        res.json({
            message: "Announcement updated successfully",
            announcement
        });
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const deleteAnnouncement = async (req, res) => {
    try {
        await announcementService.deleteAnnouncement(
            req.params.id
        );

        res.json({
            message: "Announcement deleted successfully"
        });
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    createAnnouncement,
    getAnnouncements,
    updateAnnouncement,
    deleteAnnouncement
};