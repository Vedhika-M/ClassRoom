package com.example.classroom.api

import com.example.classroom.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body request: SignupRequest
    ): AuthResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @GET("announcements")
    suspend fun getAnnouncements(): List<Announcement>

    @POST("announcements")
    suspend fun createAnnouncement(
        @Body request: CreateAnnouncementRequest
    ): Announcement

    @PUT("announcements/{id}")
    suspend fun updateAnnouncement(
        @Path("id") id: Int,
        @Body request: UpdateAnnouncementRequest
    ): Announcement

    @DELETE("announcements/{id}")
    suspend fun deleteAnnouncement(
        @Path("id") id: Int
    )

    @GET("subjects")
    suspend fun getSubjects(): List<Subject>

    @POST("subjects")
    suspend fun createSubject(
        @Body request: CreateSubjectRequest
    ): Subject

    @GET("polls")
    suspend fun getPolls(): List<Poll>

    @POST("polls")
    suspend fun createPoll(
        @Body request: CreatePollRequest
    ): Poll

    @POST("polls/{id}/vote")
    suspend fun votePoll(
        @Path("id") id: Int,
        @Body request: VoteRequest
    )

    @GET("users/students")
    suspend fun getStudents(): List<User>

    @POST("attendance/{subjectId}")
    suspend fun markAttendance(
        @Path("subjectId") subjectId: Int,
        @Body request: MarkAttendanceRequest
    )

    @GET("attendance/history/{subjectId}")
    suspend fun attendanceHistory(
        @Path("subjectId") subjectId: Int
    ): List<AttendanceHistory>

    @GET("attendance/summary/{subjectId}")
    suspend fun attendanceSummary(
        @Path("subjectId") subjectId: Int
    ): AttendanceSummary

    @Multipart
    @POST("notes/{subjectId}")
    suspend fun uploadNote(
        @Path("subjectId") subjectId: Int,
        @Part file: MultipartBody.Part,
        @Part("title") title: RequestBody
    ): Note

    @GET("notes/{subjectId}")
    suspend fun getNotes(
        @Path("subjectId") subjectId: Int
    ): List<Note>

    @GET("exams")
    suspend fun getExams(): List<Exam>

    @POST("exams/{subjectId}")
    suspend fun createExam(
        @Path("subjectId") subjectId: Int,
        @Body request: CreateExamRequest
    ): Exam

    @DELETE("exams/{examId}")
    suspend fun deleteExam(
        @Path("examId") examId: Int
    )
}