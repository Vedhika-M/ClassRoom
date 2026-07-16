package com.example.classroom.repository

import com.example.classroom.api.RetrofitInstance
import com.example.classroom.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainRepository {
    private val api = RetrofitInstance.api

    suspend fun getAnnouncements() =
        api.getAnnouncements()

    suspend fun createAnnouncement(
        title: String,
        body: String
    ) = api.createAnnouncement(
        CreateAnnouncementRequest(
            title = title,
            body = body
        )
    )

    suspend fun updateAnnouncement(
        id: Int,
        title: String,
        body: String
    ) = api.updateAnnouncement(
        id,
        UpdateAnnouncementRequest(
            title,
            body
        )
    )

    suspend fun deleteAnnouncement(id: Int) =
        api.deleteAnnouncement(id)


    suspend fun getSubjects() =
        api.getSubjects()

    suspend fun createSubject(
        name: String,
        code: String
    ) = api.createSubject(
        CreateSubjectRequest(
            name,
            code
        )
    )


    suspend fun getPolls() =
        api.getPolls()

    suspend fun createPoll(
        question: String,
        options: List<String>
    ) = api.createPoll(
        CreatePollRequest(
            question,
            options
        )
    )

    suspend fun votePoll(
        pollId: Int,
        optionId: Int
    ) = api.votePoll(
        pollId,
        VoteRequest(optionId)
    )


    suspend fun getStudents() =
        api.getStudents()

    suspend fun getAttendanceHistory(subjectId: Int) =
        api.attendanceHistory(subjectId)

    suspend fun getAttendanceSummary(subjectId: Int) =
        api.attendanceSummary(subjectId)

    suspend fun markAttendance(
        subjectId: Int,
        request: MarkAttendanceRequest
    ) = api.markAttendance(
        subjectId,
        request
    )


    suspend fun getNotes(subjectId: Int) =
        api.getNotes(subjectId)

    suspend fun uploadNote(
        subjectId: Int,
        title: String,
        file: File
    ) {
        val requestFile = file.asRequestBody(
            "application/pdf".toMediaTypeOrNull()
        )

        val body = MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestFile
        )

        val titleBody = title.toRequestBody(
            "text/plain".toMediaTypeOrNull()
        )

        api.uploadNote(
            subjectId,
            body,
            titleBody
        )
    }


    suspend fun getExams() =
        api.getExams()

    suspend fun createExam(
        subjectId: Int,
        examType: String,
        examDate: String,
        examTime: String,
        notes: String?
    ) = api.createExam(
        subjectId,
        CreateExamRequest(
            examType = examType,
            examDate = examDate,
            examTime = examTime,
            notes = notes
        )
    )

    suspend fun deleteExam(id: Int) =
        api.deleteExam(id)
}