const noteService = require("../services/noteService");

const uploadNote = async (req, res) => {
    try {
        const note = await noteService.uploadNote(
            req.params.subjectId,
            req.body.title,
            req.file,
            req.user.id
        );
        console.log("Uploaded file:", req.file);
        res.status(201).json(note);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getNotes = async (req, res) => {
    try {
        const notes = await noteService.getNotes(
            req.params.subjectId
        );
        res.json(notes);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    uploadNote,
    getNotes
};