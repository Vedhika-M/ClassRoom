const prisma = require("../prisma/prismaClient");

const uploadNote = async (
    subjectId,
    title,
    file,
    userId
) => {
    return prisma.note.create({
        data: {
            title,
            fileName: file.originalname,
            filePath: file.filename,
            subjectId: Number(subjectId),
            uploadedById: userId
        }
    });
};

const getNotes = async (subjectId) => {
    return prisma.note.findMany({
        where: {
            subjectId: Number(subjectId)
        },
        include: {
            uploadedBy: {
                select: {
                    username: true
                }
            }
        },
        orderBy: {
            uploadedAt: "desc"
        }
    });
};

module.exports = {
    uploadNote,
    getNotes
};