const prisma = require("../prisma/prismaClient");

const markAttendance = async (subjectId, date, attendance) => {
    const session = await prisma.attendanceSession.create({
        data: {
            subjectId: Number(subjectId),
            date: new Date(date)
        }
    });

    await prisma.attendanceRecord.createMany({
        data: attendance.map(student => ({
            attendanceSessionId: session.id,
            studentId: student.studentId,
            status: student.status
        }))
    });
    return session;
};

const editAttendance = async (sessionId, attendance) => {
    for (const student of attendance) {
        await prisma.attendanceRecord.update({
            where: {
                attendanceSessionId_studentId: {
                    attendanceSessionId: Number(sessionId),
                    studentId: student.studentId
                }
            },
            data: {
                status: student.status
            }
        });
    }
    return { message: "Attendance updated successfully" };
};

const getAttendanceHistory = async (subjectId, studentId) => {
    return prisma.attendanceRecord.findMany({
        where: {
            studentId: Number(studentId),
            attendanceSession: {
                subjectId: Number(subjectId)
            }
        },
        include: {
            attendanceSession: true
        },
        orderBy: {
            attendanceSession: {
                date: "asc"
            }
        }
    });
};

const getAttendanceSummary = async (subjectId, studentId) => {
    const records = await prisma.attendanceRecord.findMany({
        where: {
            studentId: Number(studentId),
            attendanceSession: {
                subjectId: Number(subjectId)
            }
        }
    });

    const present = records.filter(r => r.status === "PRESENT").length;
    const absent = records.filter(r => r.status === "ABSENT").length;
    const total = present + absent;

    return {
        totalPresent: present,
        totalAbsent: absent,
        attendancePercentage:
            total === 0 ? 0 : Number(((present / total) * 100).toFixed(2))
    };
};

module.exports = {
    markAttendance,
    editAttendance,
    getAttendanceHistory,
    getAttendanceSummary
};