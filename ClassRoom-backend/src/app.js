const express = require("express");
const cors = require("cors");
const morgan = require("morgan");
const path = require("path");

const authRoutes = require("./routes/authRoutes");
const announcementRoutes = require("./routes/announcementRoutes");
const pollRoutes = require("./routes/pollRoutes");
const subjectRoutes = require("./routes/subjectRoutes");
const attendanceRoutes = require("./routes/attendanceRoutes");
const noteRoutes = require("./routes/noteRoutes");
const examRoutes = require("./routes/examRoutes");
const userRoutes =require("./routes/userRoutes");

const app = express();

app.use(cors());
app.use(express.json());
app.use(morgan("dev"));
app.use("/uploads",express.static(path.join(__dirname, "uploads")));

app.use("/announcements", announcementRoutes);
app.use("/auth", authRoutes);
app.use("/polls", pollRoutes);
app.use("/subjects", subjectRoutes);
app.use("/attendance", attendanceRoutes);
app.use("/notes", noteRoutes);
app.use("/exams", examRoutes);
app.use( "/users", userRoutes);

app.get("/", (req, res) => {
    res.json({
        message: "ClassRoom Backend Running"
    });
});

module.exports = app;