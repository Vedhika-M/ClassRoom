package com.example.classroom.model

data class AttendanceStudent(
    val studentId: Int,
    val status: String
)

data class MarkAttendanceRequest(
    val date: String,
    val attendance: List<AttendanceStudent>
)

data class AttendanceSession(
    val date: String
)

data class AttendanceHistory(
    val status: String,
    val attendanceSession: AttendanceSession
)

data class AttendanceSummary(
    val totalPresent: Int,
    val totalAbsent: Int,
    val attendancePercentage: Double
)