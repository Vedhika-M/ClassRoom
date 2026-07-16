package com.example.classroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classroom.model.Announcement
import com.example.classroom.model.AttendanceHistory
import com.example.classroom.model.AttendanceSummary
import com.example.classroom.model.Exam
import com.example.classroom.model.MarkAttendanceRequest
import com.example.classroom.model.Note
import com.example.classroom.model.Poll
import com.example.classroom.model.Subject
import com.example.classroom.model.User
import com.example.classroom.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _polls =
        MutableStateFlow<List<Poll>>(emptyList())
    val polls: StateFlow<List<Poll>> = _polls

    private val _attendanceHistory =
        MutableStateFlow<List<AttendanceHistory>>(emptyList())
    val attendanceHistory: StateFlow<List<AttendanceHistory>> = _attendanceHistory

    private val _attendanceSaved =
        MutableStateFlow(false)
    val attendanceSaved = _attendanceSaved.asStateFlow()

    private val _attendanceSummary =
        MutableStateFlow<AttendanceSummary?>(null)

    val attendanceSummary = _attendanceSummary.asStateFlow()

    private val _exams =
        MutableStateFlow<List<Exam>>(emptyList())
    val exams: StateFlow<List<Exam>> = _exams

    private val _notes =
        MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _subjects =
        MutableStateFlow<List<Subject>>(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects

    private val _students =
        MutableStateFlow<List<User>>(emptyList())
    val students: StateFlow<List<User>> =
        _students

    private val _announcements =
        MutableStateFlow<List<Announcement>>(emptyList())
    val announcements: StateFlow<List<Announcement>> = _announcements

    fun loadAnnouncements() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _announcements.value = repository.getAnnouncements()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load announcements"
            }
            _loading.value = false
        }
    }

    fun createAnnouncement(
        title: String,
        body: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.createAnnouncement(
                    title,
                    body
                )
                loadAnnouncements()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to create announcement"
            }
            _loading.value = false
        }
    }

    fun updateAnnouncement(
        id: Int,
        title: String,
        body: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.updateAnnouncement(
                    id,
                    title,
                    body
                )

                loadAnnouncements()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to update announcement"
            }

            _loading.value = false
        }
    }

    fun deleteAnnouncement(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.deleteAnnouncement(id)
                loadAnnouncements()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to delete announcement"
            }

            _loading.value = false
        }
    }

    fun loadSubjects() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _subjects.value = repository.getSubjects()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load subjects"
            }

            _loading.value = false
        }
    }

    fun createSubject(
        name: String,
        code: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.createSubject(
                    name,
                    code
                )
                loadSubjects()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to create subject"
            }

            _loading.value = false
        }
    }

    fun loadPolls() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _polls.value = repository.getPolls()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unable to load polls"
            }
            _loading.value = false
        }
    }

    fun createPoll(
        question: String,
        options: List<String>
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.createPoll(
                    question,
                    options
                )
                loadPolls()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to create poll"
            }
            _loading.value = false
        }
    }

    fun votePoll(
        pollId: Int,
        optionId: Int
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.votePoll(
                    pollId,
                    optionId
                )
                loadPolls()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to vote"
            }
            _loading.value = false
        }
    }

    fun loadStudents() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _students.value = repository.getStudents()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load students"
            }
            _loading.value = false
        }
    }

    fun loadAttendanceHistory(subjectId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _attendanceHistory.value =
                    repository.getAttendanceHistory(subjectId)
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load attendance"
            }
            _loading.value = false
        }
    }

    fun loadAttendanceSummary(subjectId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _attendanceSummary.value = repository.getAttendanceSummary(subjectId)
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load summary"
            }
            _loading.value = false
        }
    }

    fun resetAttendanceSaved() {
        _attendanceSaved.value = false
    }

    fun markAttendance(
        subjectId: Int,
        request: MarkAttendanceRequest
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.markAttendance(
                    subjectId,
                    request
                )
                loadAttendanceHistory(subjectId)
                loadAttendanceSummary(subjectId)
                _attendanceSaved.value = true
            } catch (e: Exception) {
                _attendanceSaved.value = false
                _error.value = e.message ?: "Unable to mark attendance"
            }
            _loading.value = false
        }
    }

    fun loadNotes(subjectId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _notes.value =
                    repository.getNotes(subjectId)
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load notes"
            }
            _loading.value = false
        }
    }

    fun uploadNote(
        subjectId: Int,
        title: String,
        file: File
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.uploadNote(
                    subjectId,
                    title,
                    file
                )

                loadNotes(subjectId)
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to upload note"

            }
            _loading.value = false
        }
    }

    fun loadExams() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _exams.value =
                    repository.getExams()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to load exams"
            }
            _loading.value = false
        }
    }

    fun createExam(
        subjectId: Int,
        examType: String,
        examDate: String,
        examTime: String,
        notes: String?
    ) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.createExam(
                    subjectId,
                    examType,
                    examDate,
                    examTime,
                    notes
                )

                loadExams()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to create exam"
            }
            _loading.value = false
        }
    }

    fun deleteExam(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.deleteExam(id)
                loadExams()
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Unable to delete exam"
            }
            _loading.value = false
        }
    }
}