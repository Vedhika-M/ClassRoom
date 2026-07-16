package com.example.classroom.model

data class Poll(
    val id: Int,
    val question: String,
    val options: List<PollOption>,
    val isClosed: Boolean,
    val hasVoted: Boolean
)

data class PollOption(
    val id: Int,
    val option: String,
    val votes: Int = 0
)

data class CreatePollRequest(
    val question: String,
    val options: List<String>
)

data class VoteRequest(
    val optionId: Int
)