package com.example.classroom.model

data class Announcement(
    val id: Int,
    val title: String,
    val body: String,
    val createdAt: String,
    val createdById: Int,
    val createdBy: AnnouncementUser
)

data class AnnouncementUser(
    val id: Int,
    val username: String
)

data class CreateAnnouncementRequest(
    val title: String,
    val body: String
)

data class UpdateAnnouncementRequest(
    val title: String,
    val body: String
)