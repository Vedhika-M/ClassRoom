const prisma = require("../prisma/prismaClient");

const createExam = async (
    subjectId,
    examType,
    examDate,
    examTime,
    notes
) => {
    return prisma.exam.create({
        data: {
            subjectId: Number(subjectId),
            examType,
            examDate: new Date(examDate),
            examTime,
            notes
        }
    });
};

const deleteExam = async (examId) => {
    return prisma.exam.delete({
        where: {
            id: Number(examId)
        }
    });
};

const getExams = async () => {
    return prisma.exam.findMany({
        include: {
            subject: true
        },
        orderBy: {
            examDate: "asc"
        }
    });
};

module.exports = {
    createExam,
    deleteExam,
    getExams
};