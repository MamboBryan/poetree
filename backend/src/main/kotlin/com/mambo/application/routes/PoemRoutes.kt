package com.mambo.application.routes

import com.mambo.application.utils.*
import com.mambo.data.dao.BookmarkDao
import com.mambo.data.dao.CommentsDao
import com.mambo.data.dao.LikesDao
import com.mambo.data.dao.PoemsDao
import com.mambo.data.requests.CommentRequest
import com.mambo.data.requests.PoemRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.poemRoutes() {

    val poemsDao = PoemsDao()
    val bookmarkDao = BookmarkDao()
    val likesDao = LikesDao()
    val commentsDao = CommentsDao()

    route("poems") {

        // create poem
        post {

            val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
            )

            val request = call.receive<PoemRequest>()

            val response = poemsDao.create(userId = currentUserId, request = request)

            call.respond(response)

        }

        // get poem list, enables searching by string (q), topic and page
        get {

            val currentUserId = call.getCurrentUserId() ?: return@get call.defaultResponse(
                status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
            )

            val query = call.getQuery("q")

            val topic = call.getQuery("topic")

            val page = call.getQuery("page")?.toIntOrNull() ?: 1

            if (topic.isNullOrBlank().not()) {
                if (topic?.toIntOrNull() == null) return@get call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid topic id"
                )
            }

            val response = poemsDao.getPoems(
                userId = currentUserId,
                topic = topic?.toIntOrNull(),
                queryString = query,
                page = page
            )

            call.respond(response)

        }

        route("poem") {

            // get poem
            post {

                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = poemsDao.getPoem(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // update poem
            put {
                val currentUserId = call.getCurrentUserId() ?: return@put call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val response = poemsDao.update(userId = currentUserId, request = request)
                call.respond(response)

            }

            // delete poem
            delete {
                val currentUserId = call.getCurrentUserId() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = poemsDao.delete(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // mark poem as read
            post("read") {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = poemsDao.markAsRead(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // bookmark poem
            post("bookmark") {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = bookmarkDao.create(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // un-bookmark poem
            delete("un-bookmark") {
                val currentUserId = call.getCurrentUserId() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = bookmarkDao.delete(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // like poem
            post("like") {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = likesDao.likePoem(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // unlike a poem
            delete("unlike") {
                val currentUserId = call.getCurrentUserId() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PoemRequest>()

                val poemId = request.poemId.asUUID() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = likesDao.unlikePoem(userId = currentUserId, poemId = poemId)
                call.respond(response)
            }

            // poem comments
            post("comments") {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val page = call.getQuery("page")?.toIntOrNull() ?: 1

                val request = call.receive<CommentRequest>()

                val poemId = request.poemId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid poem id"
                )

                val response = commentsDao.getComments(userId = currentUserId, poemId = poemId, page = page)
                call.respond(response)
            }

        }


    }

}