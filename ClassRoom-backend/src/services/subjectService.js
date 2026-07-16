const prisma = require("../prisma/prismaClient");

const createSubject = async (name, code) => {
    return await prisma.subject.create({
        data: {
            name,
            code
        }
    });
};

const getSubjects = async () => {
    return await prisma.subject.findMany({
        orderBy: {
            name: "asc"
        }
    });
};

module.exports = {
    createSubject,
    getSubjects
};