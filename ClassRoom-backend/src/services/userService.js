const prisma = require("../prisma/prismaClient");

const getStudents = async () => {
    return prisma.user.findMany({
        select: {
            id: true,
            username: true,
            role: true
        },
        orderBy: {
            username: "asc"
        }
    });
};
module.exports = {
    getStudents
};