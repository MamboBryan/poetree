package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.remote.CreateCommentRequest
import com.mambo.poetree.data.remote.PoemsApi
import com.mambo.poetree.data.remote.UpdateCommentRequest

class CommentRepository {

    private val poemsApi: PoemsApi = PoemsApi()

    // SINGLE

    suspend fun create(request: CreateCommentRequest) = poemsApi.createComment(request = request)

    suspend fun update(request: UpdateCommentRequest) = poemsApi.updateComment(request = request)

    suspend fun delete(commentId: String) = poemsApi.deleteComment(commentId = commentId)

    suspend fun get(commentId: String) = poemsApi.getComment(commentId = commentId)

    // MULTIPLE

    // TODO: update get comments paging
    suspend fun getComment(poemId: String, page: Int) =
        poemsApi.getComments(poemId = poemId, page = page)

}
