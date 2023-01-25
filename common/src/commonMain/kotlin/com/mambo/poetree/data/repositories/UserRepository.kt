package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.*

class UserRepository {

    private val poemsApi: PoemsApi = PoemsApi()
    private val preferences: UserPreferences = UserPreferences()

    suspend fun getMyDetails() = poemsApi.getMyDetails()

    suspend fun getUserDetails(userId: String) = poemsApi.getUser(userId = userId)

    suspend fun setup(request: SetupRequest): NetworkResult<String> {

        val response = poemsApi.userSetup(request)

        if (response.isSuccessful.not()){
            return NetworkResult(false, message = response.message, data = null)
        }

        response.data?.toUser()?.let {
            preferences.saveUserDetails(details = it)
            preferences.userHasSetup()
        }

        return NetworkResult(true, message = response.message, data = null)
    }

    suspend fun updateUser(request: UserUpdateRequest): NetworkResult<String> {
        val response = poemsApi.updateUser(request)

        if (response.isSuccessful.not()){
            return NetworkResult(false, message = response.message, data = null)
        }

        response.data?.toUser()?.let { preferences.saveUserDetails(details = it) }

        return NetworkResult(true, message = response.message, data = null)
    }

    suspend fun updateToken(token: String) = poemsApi.uploadMessagingToken(token)

    suspend fun updateImageUrl(url: String) = poemsApi.uploadImageUrl(url)

    suspend fun updatePassword(request: UpdatePasswordRequest) = poemsApi.updatePassword(request)

    suspend fun getUsers() = poemsApi.getUsers()

}