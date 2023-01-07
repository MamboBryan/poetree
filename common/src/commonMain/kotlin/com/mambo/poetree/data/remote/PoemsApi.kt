package com.mambo.poetree.data.remote

import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.dto.*
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
class PoemsApi {

    enum class Endpoints(val path: String) {
        HOME("/"),
        SIGN_IN("auth/signin"),
        SIGN_UP("auth/signup"),
        REFRESH_TOKEN("auth/refresh"),
        RESET("auth/reset"),
        USERS("users"),
        USER("users/user"),
        USER_ME("users/me"),
        TOPICS("topics"),
        TOPIC("topics/topic"),
        POEMS("poems"),
        POEM("poems/poem"),
        COMMENTS("comments"),
        COMMENT("comments/comment"),
        ;

        /**
         * To get the local server testing port
         * 1. Run the server
         * 2. Run `ifconfig | grep "inet " | grep -v 127.0.0.1` in your terminal
         * 3. change the return value of true below to the value retrieved in step 2
         */

        private val BASE_URL = when (true) {
            true -> "http://192.168.88.253:8080/v1/" // change here
            false -> "https://mambo-poetree.herokuapp.com/v1/"
        }

        val url: String
            get() = BASE_URL + this.path

    }

    private val client = HttpClient {

//        engine {
//            this.addInterceptor(networkInterceptor)
//            this.addInterceptor(authInterceptor)
//            this.addInterceptor(loggingInterceptor)
//        }

        install(ContentNegotiation) {
            json(json = Json {
                encodeDefaults = false
                explicitNulls = false
            })
        }

        install(Logging) {
            level = LogLevel.BODY
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(message)
                }
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${UserPreferences().accessToken}")
        }

    }


    private suspend fun <T> safeApiCall(
        block: suspend () -> NetworkResult<T>
    ): NetworkResult<T> {
        return try {
            return block()
        } catch (e: Exception) {
            val message = e.message ?: "Error"
            Napier.e(message = message)
            NetworkResult(isSuccessful = false, message = message)
        }
    }

    /**
     * DUMMY
     */

    suspend fun goofy() = safeApiCall<GoofyResponse> {
        val response = client.get(Endpoints.HOME.url)
        response.body()
    }

    /**
     * AUTH
     */

    suspend fun signIn(request: AuthRequest) = safeApiCall<AuthResponse> {
        val response = client.post(Endpoints.SIGN_IN.url) { setBody(request) }
        response.body()
    }

    suspend fun signUp(request: AuthRequest) = safeApiCall<AuthResponse> {
        val response = client.post(Endpoints.SIGN_UP.url) { setBody(request) }
        response.body()
    }

    suspend fun refreshToken(token: String) = safeApiCall<TokenResponse> {
        val response = client.post(Endpoints.REFRESH_TOKEN.url) {
            setBody(mapOf("refreshToken" to token))
        }
        response.body()
    }

    suspend fun reset(email: String) = safeApiCall<Boolean> {
        val response = client.post(Endpoints.RESET.url) {
            setBody(mapOf("email" to email))
        }
        response.body()
    }

    /**
     * CURRENT USER
     */

    suspend fun getMyDetails() = safeApiCall<UserCompleteDto> {
        val response = client.get(Endpoints.USER_ME.url)
        response.body()
    }

    suspend fun userSetup(request: SetupRequest) = safeApiCall<UserCompleteDto> {
        val url = Endpoints.USER_ME.url.plus("/setup")
        val response = client.post(url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun updateUser(request: UserUpdateRequest) = safeApiCall<UserCompleteDto> {
        val url = Endpoints.USER_ME.url.plus("/update")
        val response = client.put(url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun updatePassword(request: UpdatePasswordRequest) = safeApiCall<TokenResponse> {
        val url = Endpoints.USER_ME.url.plus("/update-password")
        val response = client.put(url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun uploadMessagingToken(token: String) = safeApiCall<UserCompleteDto> {
        val url = Endpoints.USER_ME.url.plus("/update")
        val response = client.put(url) {
            setBody(mapOf("token" to token))
        }
        response.body()
    }

    suspend fun uploadImageUrl(imageUrl: String) = safeApiCall<UserCompleteDto> {
        val url = Endpoints.USER_ME.url.plus("/update")
        val response = client.put(url) {
            setBody(mapOf("imageUrl" to imageUrl))
        }
        response.body()
    }

    suspend fun deleteAccount() = safeApiCall<Boolean> {
        val response = client.delete(Endpoints.USER_ME.url.plus("/delete"))
        response.body()
    }

    /**
     * USERS
     */

    suspend fun getUser(userId: String) = safeApiCall<UserCompleteDto> {
        val response = client.post(Endpoints.USER.url) {
            setBody(mapOf("userId" to userId))
        }
        response.body()
    }

    suspend fun getUsers() = safeApiCall<PagedResult<UserMinimalDto>> {
        val response = client.get(Endpoints.USERS.url) { }
        response.body()
    }

    suspend fun searchUsers(query: String) = safeApiCall<PagedResult<UserMinimalDto>> {
        val response = client.get(Endpoints.USERS.url) {
            parameter("name", query)
        }
        response.body()
    }

    /**
     * TOPICS
     */

    suspend fun createTopic(request: TopicRequest) = safeApiCall<TopicDto> {
        val response = client.post(Endpoints.TOPICS.url) { setBody(request) }
        response.body()
    }

    suspend fun updateTopic(topicId: Int, request: TopicRequest) = safeApiCall<TopicDto> {
        val url = Endpoints.TOPIC.url.plus("/$topicId")
        val response = client.put(url) { setBody(request) }
        response.body()
    }

    suspend fun getTopic(topicId: Int) = safeApiCall<TopicDto> {
        val url = Endpoints.TOPICS.url.plus("/$topicId")
        val response = client.get(url)
        response.body()
    }

    suspend fun deleteTopic(topicId: Int) = safeApiCall<Boolean> {
        val url = Endpoints.TOPICS.url.plus("/$topicId")
        val response = client.delete(url)
        response.body()
    }

    suspend fun getTopics(page: Int = 1) = safeApiCall<PagedResult<TopicDto>> {
        val url = Endpoints.TOPICS.url
        val response = client.get(url) {
            url { parameters.append("page", page.toString()) }
        }
        response.body()
    }

    /**
     * POEMS
     */

    suspend fun createPoem(request: CreatePoemRequest) = safeApiCall<PoemDto> {
        val url = Endpoints.POEMS.url
        val response = client.post(url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun getPoem(request: PoemRequest) = safeApiCall<PoemDto> {
        val url = Endpoints.POEM.url
        val response = client.post(url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun updatePoem(request: EditPoemRequest) = safeApiCall<PoemDto> {
        val url = Endpoints.POEM.url
        val response = client.put(url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun deletePoem(poemId: String) = safeApiCall<Boolean> {
        val url = Endpoints.POEM.url
        val response = client.delete(url) {
            setBody(mapOf("poemId" to poemId))
        }
        response.body()
    }

    suspend fun markPoemAsRead(poemId: String) = safeApiCall<Boolean> {
        val url = Endpoints.POEM.url.plus("/read")
        val response = client.post(url) {
            setBody(mapOf("poemId" to poemId))
        }
        response.body()
    }

    suspend fun getPoems(page: Int = 1) = safeApiCall<PagedResult<PoemDto>> {
        val url = Endpoints.POEMS.url
        val response = client.get(url) {
            parameter("page", page.toString())
        }
        response.body()
    }

    suspend fun getUserPoems(userId: String, page: Int = 1) =
        safeApiCall<PagedResult<PoemDto>> {
            val url = Endpoints.USER.url.plus("/poems")
            val response = client.post(url) {
                setBody(mapOf("userId" to userId))
                parameter("page", page.toString())
            }
            response.body()
        }

    suspend fun searchPoems(query: String, topicId: Int?, page: Int = 1) =
        safeApiCall<PagedResult<PoemDto>> {
            val url = Endpoints.POEMS.url
            val response = client.get(url) {
                if (topicId != null) parameter("topic", topicId.toString())
                parameter("q", query)
                parameter("page", page.toString())
            }
            response.body()
        }

    /**
     * COMMENTS
     */

    suspend fun createComment(request: CreateCommentRequest) = safeApiCall<CommentDto> {
        val response = client.post(Endpoints.COMMENTS.url) {
            setBody(request)
        }
        response.body()
    }

    suspend fun updateComment(request: UpdateCommentRequest) =
        safeApiCall<CommentCompleteDto> {
            val response = client.put(Endpoints.COMMENT.url) {
                setBody(request)
            }
            response.body()
        }

    suspend fun getComment(commentId: String) = safeApiCall<CommentCompleteDto> {
        val response = client.post(Endpoints.COMMENT.url) {
            setBody(mapOf("commentId" to commentId))
        }
        response.body()
    }

    suspend fun getComments(poemId: String, page: Int = 1) =
        safeApiCall<PagedResult<CommentCompleteDto>> {

            val url = Endpoints.POEM.url.plus("/comments")

            val response = client.post(url) {
                setBody(mapOf("poemId" to poemId))
                parameter("page", page.toString())
            }
            response.body()
        }

    suspend fun deleteComment(commentId: String) = safeApiCall<Boolean> {
        val response = client.delete(Endpoints.COMMENT.url) {
            setBody(mapOf("commentId" to commentId))
        }
        response.body()
    }

    /**
     * BOOKMARKS
     */

    suspend fun bookmarkPoem(poemId: String) = safeApiCall<Boolean> {
        val url = Endpoints.POEM.url.plus("/bookmark")
        val response = client.post(url) {
            setBody(mapOf("poemId" to poemId))
        }
        response.body()
    }

    suspend fun unBookmarkPoem(poemId: String) = safeApiCall<Boolean> {
        val url = Endpoints.POEM.url.plus("/un-bookmark")
        val response = client.delete(url) {
            setBody(mapOf("poemId" to poemId))
        }
        response.body()
    }

    /**
     * LIKES
     */

    suspend fun likePoem(poemId: String) = safeApiCall<Boolean> {
        val url = Endpoints.POEM.url.plus("/like")
        val response = client.post(url) {
            setBody(mapOf("poemId" to poemId))
        }
        response.body()
    }

    suspend fun unLikePoem(poemId: String) = safeApiCall<Boolean> {
        val url = Endpoints.POEM.url.plus("/unlike")
        val response = client.delete(url) {
            setBody(mapOf("poemId" to poemId))
        }
        response.body()
    }

    suspend fun likeComment(commentId: String) = safeApiCall<Boolean> {
        val url = Endpoints.COMMENT.url.plus("/like")
        val response = client.post(url) {
            setBody(mapOf("commentId" to commentId))
        }
        response.body()
    }

    suspend fun unLikeComment(commentId: String) = safeApiCall<Boolean> {
        val url = Endpoints.COMMENT.url.plus("/unlike")
        val response = client.delete(url) {
            setBody(mapOf("commentId" to commentId))
        }
        response.body()
    }

    /**
     * IMAGE
     */

    suspend fun uploadImage(form: MultiPartFormDataContent) = safeApiCall<String> {
        val url = "https://us-central1-poetree-254.cloudfunctions.net/uploadProfile"
        val response = client.post(url){
            setBody(form)
        }
        response.body()
    }

    suspend fun deleteImage(userId: String) = safeApiCall<Boolean> {
        val url = "https://us-central1-poetree-254.cloudfunctions.net/deleteProfile"
        val response = client.post(url){
            setBody(Pair("userId", userId))
        }
        response.body()
    }

}