package com.mambo.poetree.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResult<T>(
    @SerialName("success")
    val isSuccessful: Boolean,
    val message: String,
    val data: T? = null
)

@Serializable
data class PagedResult<T>(
    val current: Int?,
    val next: Int?,
    val previous: Int?,
    val list: List<T>
)