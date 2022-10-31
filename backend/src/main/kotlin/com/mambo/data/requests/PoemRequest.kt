package com.mambo.data.requests

data class PoemRequest(
    val poemId: String?,
    val title: String?,
    val content: String?,
    val html: String?,
    val topic: Int?,
)