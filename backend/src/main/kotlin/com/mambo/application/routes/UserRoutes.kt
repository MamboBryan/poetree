package com.mambo.application.routes

import com.mambo.application.plugins.hash
import com.mambo.application.utils.*
import com.mambo.data.dao.PoemsDao
import com.mambo.data.dao.UsersDao
import com.mambo.data.helpers.TokenProvider
import com.mambo.data.requests.PasswordResetRequest
import com.mambo.data.requests.UserRequest
import com.mambo.data.requests.UserUpdateRequest
import com.mambo.data.tables.user.User
import io.github.aakira.napier.Napier
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.userRoutes(
    issuer: String, audience: String
) {

    val usersDao = UsersDao()
    val poemsDao = PoemsDao()

    route("users") {

        get {

            val userId = call.getCurrentUserId() ?: return@get call.defaultResponse(
                status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
            )

            val name = call.getQuery(queryName = QueryUtils.NAME) ?: ""

            val page = call.getQuery(QueryUtils.PAGE)?.toInt() ?: 1

            val response = usersDao.getUsers(userId = userId, query = name, page = page)

            return@get call.respond(response)

        }

        route("user") {

            post {

                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<UserRequest>()

                if (request.userId.isNullOrBlank()) return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "User Id cannot be null or blank"
                )

                val userId = request.userId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid user id"
                )

                if (currentUserId == userId) return@post call.redirectInternally("/users/me")

                val response = usersDao.getUserDetails(userId = userId)

                return@post call.respond(response)

            }

            post("poems") {
                val currentUserId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<UserRequest>()

                if (request.userId.isNullOrBlank()) return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "User Id cannot be null or blank"
                )

                val userId = request.userId.asUUID() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid user id"
                )

                val page = call.getQuery(QueryUtils.PAGE)?.toIntOrNull() ?: 1

                val response = poemsDao.getUserPoems(currentUserId = currentUserId, userId = userId, page = page)

                return@post call.respond(response)
            }

        }

        route("me") {

            get {

                val userId = call.getCurrentUserId() ?: return@get call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val response = usersDao.getUserDetails(userId = userId)

                call.respond(response)

            }

            post("setup") {

                val userId = call.getCurrentUserId() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<UserUpdateRequest>()

                if (request.username.isNullOrBlank() or request.bio.isNullOrBlank() or request.dateOfBirth.isNullOrBlank() or (request.gender == null)) return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Enter all fields to continue"
                )

                val date = request.dateOfBirth.toDate() ?: return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Invalid date parsed"
                )

                if (date.isValidAge().not()) return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "User Should be 15 years or older"
                )

                if ((request.gender == null) or (request.gender !in 0..1)) return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Invalid gender"
                )

                if ((request.bio != null) && (request.bio.length > 125)) return@post call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Bio should not be null or above 125 characters"
                )

                val response = usersDao.update(
                    id = userId,
                    email = request.email,
                    username = request.username,
                    bio = request.bio,
                    dateOfBirth = request.dateOfBirth,
                    gender = request.gender,
                )

                call.respond(response)

            }

            put("update") {

                val userId = call.getCurrentUserId() ?: return@put call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<UserUpdateRequest>()

                if (request.username.isNullOrBlank() && request.bio.isNullOrBlank() && request.email.isNullOrBlank() && request.dateOfBirth.isNullOrBlank() && request.imageUrl.isNullOrBlank() && request.token.isNullOrBlank() && (request.gender == null)) return@put call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "All fields cannot be blank"
                )

                if (request.email != null && !request.email.isValidEmail()) return@put call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Invalid Email"
                )

                if ((request.gender != null) && (request.gender !in 0..1)) return@put call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Invalid gender"
                )

                if (request.dateOfBirth != null) {

                    val date = request.dateOfBirth.toDate() ?: return@put call.defaultResponse(
                        status = HttpStatusCode.BadRequest, "Invalid date parsed"
                    )

                    if (date.isValidAge().not()) return@put call.defaultResponse(
                        status = HttpStatusCode.BadRequest, "User Should be 15 years or older"
                    )
                }

                if (request.imageUrl.isNullOrBlank().not()) {
                    if (request.imageUrl.isValidUrl().not()) return@put call.defaultResponse(
                        status = HttpStatusCode.BadRequest, "Invalid image url."
                    )
                }

                if ((request.bio != null) && (request.bio.length > 140)) return@put call.defaultResponse(
                    status = HttpStatusCode.BadRequest, "Bio should not be null or above 140 characters"
                )

                val response = usersDao.update(
                    id = userId,
                    email = request.email,
                    username = request.username,
                    bio = request.bio,
                    dateOfBirth = request.dateOfBirth,
                    gender = request.gender,
                    imageUrl = request.imageUrl,
                    token = request.token
                )

                call.respond(response)
            }

            put("update-password") {

                val userId = call.getCurrentUserId() ?: return@put call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed"
                )

                val request = call.receive<PasswordResetRequest>()

                if (request.oldPassword.isNullOrBlank() or request.newPassword.isNullOrBlank()) return@put call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "Invalid Request Details"
                )

                if (request.oldPassword.equals(request.newPassword)) return@put call.defaultResponse(
                    status = HttpStatusCode.BadRequest, message = "New Password cannot be same as old password"
                )

                return@put try {

                    val response = usersDao.getUser(userId = userId, formatToDto = false)

                    val user = when (response.status.isSuccess()) {
                        true -> response.data as User
                        else -> return@put call.respond(response)
                    }

                    val hash = hash(request.oldPassword!!)

                    if (user.hash != hash) return@put call.defaultResponse(
                        status = HttpStatusCode.Unauthorized, message = "Invalid Credentials"
                    )

                    val newHash = hash(request.newPassword!!)

                    val updateResponse = usersDao.updatePassword(userId, newHash)

                    if (updateResponse.status.isSuccess().not()) return@put call.respond(updateResponse)

                    /**
                     * TODO:
                     * 1. Invalidate user token
                     */
                    val token =
                        TokenProvider.generateTokens(issuer = issuer, audience = audience, userId = user.id.toString())

                    val data = mapOf("token" to token)

                    call.successWithData(
                        status = HttpStatusCode.OK, message = "Password updated successfully", data = data
                    )

                } catch (e: Exception) {
                    Napier.e(e.localizedMessage, e)
                    call.defaultResponse(status = HttpStatusCode.NotAcceptable, message = "Failed updating password")
                }

            }

            delete("delete") {

                val id = call.getCurrentUserId() ?: return@delete call.defaultResponse(
                    status = HttpStatusCode.Unauthorized, message = "Authentication Failed",
                )

                val response = usersDao.delete(id)

                /**
                 * TODO:
                 * 1. Invalidate user token
                 */

                return@delete call.respond(response)

            }

        }

    }

}