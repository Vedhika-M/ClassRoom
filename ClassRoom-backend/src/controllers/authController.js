const authService = require("../services/authService");

const register = async (req, res) => {
    try {
        const {username, email, password,isCR,crSecret} = req.body;

        const result = await authService.register(
            username,
            email,
            password,
            isCR,
            crSecret
        );

        res.status(201).json({
            message: "User registered successfully",
            token: result.token,
            user: result.user
        });

    } catch (error) {
    console.error(error);

    res.status(400).json({
        message: error.message
    });
}
};

const login = async (req, res) => {
    try {
        const { email, password } = req.body;

        const result = await authService.login(
            email,
            password
        );

        res.status(200).json({
            message: "Login successful",
            token: result.token,
            user: result.user
        });

    } catch (error) {
        console.error(error);

        res.status(400).json({
            message: error.message
        });
    }
};

const getProfile = async (req, res) => {
    try {
        const user = await authService.getProfile(req.user.id);
        res.status(200).json(user);
    } catch (error) {
        console.error(error);
        res.status(400).json({
            message: error.message
        });
    }
};

module.exports = {
    register,
    login,
    getProfile
};