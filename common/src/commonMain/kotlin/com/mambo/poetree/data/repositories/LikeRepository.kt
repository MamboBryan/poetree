package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.remote.PoemsApi

class LikeRepository {

    private val poemsApi: PoemsApi = PoemsApi()

    suspend fun likePoem(poemId: String) = poemsApi.likePoem(poemId = poemId)

    suspend fun unLikePoem(poemId: String) = poemsApi.unLikePoem(poemId = poemId)

    suspend fun likeComment(commentId: String) = poemsApi.likeComment(commentId = commentId)

    suspend fun unLikeComment(commentId: String) = poemsApi.unLikeComment(commentId = commentId)

}
