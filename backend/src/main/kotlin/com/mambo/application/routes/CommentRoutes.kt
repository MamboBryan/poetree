package com.mambo.application.routes

import com.mambo.application.utils.asUUID
import com.mambo.application.utils.defaultResponse
import com.mambo.application.utils.getCurrentUserId
import com.mambo.application.utils.respond
import com.mambo.data.dao.CommentsDao
import com.mambo.data.dao.LikesDao
import com.mambo.data.requests.CommentRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.commentRoutes() {

    val commentsDao = CommentsDao()
    val likesDao = LikesDao()

    route("comments") {

        // create comment
        post {
            val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
            )

            val request = call.receive<CommentRequest>()

            val poemId = request.poemId.asUUID() ?: return@post call.defaultResponse(
                status = HttpStatusCode.BadRequest, message = "Invalid poem id"
            )

            val response = commentsDao.create(userID = currentUserId, poemId = poemId, request = request)
            call.respond(response)
        }

        route("comment"){

            // get comment
            post {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<CommentRequest>()

                val commentId = request.commentId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid comment id"
                )

                val response = commentsDao.getComment(userId = currentUserId, commentId = commentId)
                call.respond(response)
            }

            // update comment
            put {
                val currentUserId = call.getCurrentUserId() ?: return@put call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<CommentRequest>()

                val response = commentsDao.update(userId = currentUserId, request = request)
                call.respond(response)
            }

            // delete comment
            delete {

                val currentUserId = call.getCurrentUserId() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<CommentRequest>()

                val commentId = request.commentId.asUUID() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid comment id"
                )
                val response = commentsDao.delete(userId = currentUserId, commentId = commentId)
                call.respond(response)
            }

            // like comment
            post("like") {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<CommentRequest>()

                val commentId = request.commentId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid comment id"
                )

                val response = likesDao.likeComment(userId = currentUserId, commentId = commentId)
                call.respond(response)
            }

            // unlike comment
            delete("unlike") {

                val currentUserId = call.getCurrentUserId() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<CommentRequest>()

                val commentId = request.commentId.asUUID() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid comment id"
                )
                val response = likesDao.unlikeComment(userId = currentUserId, commentId = commentId)
                call.respond(response)
            }

        }

    }

}