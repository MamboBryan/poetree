package com.mambo.data.requests

data class UserRequest(
    val userId: String?
)

data class UserUpdateRequest(
    val email: String?,
    val username: String?,
    val bio: String?,
    val dateOfBirth: String?,
    val gender: Int?,
    val imageUrl: String?,
    val token: String?
)