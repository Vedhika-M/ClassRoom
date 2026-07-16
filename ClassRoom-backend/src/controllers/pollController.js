const pollService = require("../services/pollService");

const createPoll = async (req, res) => {
    try {
        const { question, options } = req.body;

        const poll = await pollService.createPoll(
            question,
            options,
            req.user.id
        );

        res.status(201).json({
            message: "Poll created successfully",
            poll
        });
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

const getPolls = async (req, res) => {
    try {
        const polls = await pollService.getPolls(req.user.id);
        res.json(polls);
    } catch (error) {
        console.error(error);

        res.status(400).json({
            message: error.message
        });
    }
};

const votePoll = async (req, res) => {
    try {
        const { optionId } = req.body;

        const vote = await pollService.votePoll(
            req.params.id,
            optionId,
            req.user.id
        );

        res.status(201).json({
            message: "Vote recorded successfully",
            vote
        });
    } catch (error) {
        console.error(error);

        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    createPoll,
    getPolls,
    votePoll
};