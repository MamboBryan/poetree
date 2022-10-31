package com.mambo.data.requests

data class PasswordResetRequest(
    val oldPassword: String?,
    val newPassword: String?
)