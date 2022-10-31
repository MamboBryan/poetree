package com.mambo.data.requests

data class CommentRequest(
    val poemId: String?,
    val commentId: String?,
    val content : String?
)