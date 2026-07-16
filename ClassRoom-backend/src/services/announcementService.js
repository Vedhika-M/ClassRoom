const prisma = require("../prisma/prismaClient");

const createAnnouncement = async (title, body, userId) => {
    const announcement = await prisma.announcement.create({
        data: {
            title,
            body,
            createdById: userId
        }
    });
    return announcement;
};

const getAnnouncements = async () => {
    return await prisma.announcement.findMany({
        include: {
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
};

const updateAnnouncement = async (id, title, body) => {
    const existingAnnouncement = await prisma.announcement.findUnique({
        where: {
            id: Number(id)
        }
    });

    if (!existingAnnouncement) {
        throw new Error("Announcement not found");
    }

    const updatedAnnouncement = await prisma.announcement.update({
        where: {
            id: Number(id)
        },
        data: {
            title,
            body
        }
    });
    return updatedAnnouncement;
};

const deleteAnnouncement = async (id) => {
    const announcement = await prisma.announcement.findUnique({
        where: {
            id: Number(id)
        }
    });

    if (!announcement) {
        throw new Error("Announcement not found");
    }

    await prisma.announcement.delete({
        where: {
            id: Number(id)
        }
    });
};

module.exports = {
    createAnnouncement,
    getAnnouncements,
    updateAnnouncement,
    deleteAnnouncement
};