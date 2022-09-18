package com.mambo.poetree.data.remote

import com.mambo.poetree.data.remote.dto.UserCompleteDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: TokenResponse,
    val user: UserCompleteDto
)

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)