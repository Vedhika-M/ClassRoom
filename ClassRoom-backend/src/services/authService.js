const bcrypt = require("bcrypt");
const prisma = require("../prisma/prismaClient");
const {generateToken} = require("../utils/jwt");

const register = async (username, email, password, isCR, crSecret) => {
    const existingUsername = await prisma.user.findUnique({
        where: {
            username: username
        }
    });

    if (existingUsername) {
        throw new Error("Username already exists");
    }

    const existingEmail = await prisma.user.findUnique({
        where: {
            email: email
        }
    });

    if (existingEmail) {
        throw new Error("Email already exists");
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    let role = "STUDENT";

    if (isCR) {
        if (crSecret !== process.env.CR_SECRET) {
            throw new Error("Invalid CR Secret");
        }
        role = "CR";
    }

    const user = await prisma.user.create({
        data: {
            username,
            email,
            password: hashedPassword,
            role
        }
    });

    const token = generateToken(user);
    const { password: _, ...userWithoutPassword } = user;

return {
    token,
    user: userWithoutPassword
};
};

const login = async (email, password) => {
    const user = await prisma.user.findUnique({
        where: {
            email: email
        }
    });

    if (!user) {
        throw new Error("Invalid email or password");
    }

    const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
        throw new Error("Invalid email or password");
    }

    const token = generateToken(user);
    const { password: _, ...userWithoutPassword } = user;

    return {
        token,
        user: userWithoutPassword
    };
};

const getProfile = async (userId) => {
    const user = await prisma.user.findUnique({
        where: {
            id: userId
        }
    });

    if (!user) {
        throw new Error("User not found");
    }

    const { password: _, ...userWithoutPassword } = user;
    return userWithoutPassword;
};

module.exports = {
     register, 
     login,
     getProfile 
    };