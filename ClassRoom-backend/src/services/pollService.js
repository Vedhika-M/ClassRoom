const prisma = require("../prisma/prismaClient");

const createPoll = async (question, options, userId) => {
    return await prisma.poll.create({
        data: {
            question,
            createdById: userId,
            options: {
                create: options.map(option => ({
                    option
                }))
            }
        },
        include: {
            options: true
        }
    });
};

const getPolls = async (userId) => {
    const polls = await prisma.poll.findMany({
        include: {
            options: {
                include: {
                    votes: true
                }
            },
            votes: true,
            createdBy: {
                select: {
                    id: true,
                    username: true
                }
            }
        },
        orderBy: {
            createdAt: "desc"
        }
    });

    return polls.map(poll => ({
        ...poll,
        hasVoted: poll.options.some(option =>
            option.votes.some(vote => vote.userId === userId)
        ),
        options: poll.options.map(option => ({
            id: option.id,
            option: option.option,
            votes: option.votes.length
        }))
    }));
};

const votePoll = async (pollId, optionId, userId) => {
    const poll = await prisma.poll.findUnique({
        where: {
            id: Number(pollId)
        }
    });

    if (!poll) {
        throw new Error("Poll not found");
    }

    if (poll.isClosed) {
        throw new Error("Poll is closed");
    }

    const existingVote = await prisma.vote.findUnique({
        where: {
            userId_pollId: {
                userId,
                pollId: Number(pollId)
            }
        }
    });

    if (existingVote) {
        throw new Error("You have already voted");
    }
    return await prisma.vote.create({
        data: {
            userId,
            pollId: Number(pollId),
            optionId: Number(optionId)
        }
    });
};

module.exports = {
    createPoll,
    getPolls,
    votePoll
};