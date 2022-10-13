package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.remote.PoemsApi
import com.mambo.poetree.data.remote.SetupRequest
import com.mambo.poetree.data.remote.UpdatePasswordRequest
import com.mambo.poetree.data.remote.UserUpdateRequest

class UserRepository {

    private val poemsApi: PoemsApi = PoemsApi()

    suspend fun getMyDetails() = poemsApi.getMyDetails()

    suspend fun getUserDetails(userId: String) = poemsApi.getUser(userId = userId)

    suspend fun setup(request: SetupRequest) = poemsApi.userSetup(request)

    suspend fun updateUser(request: UserUpdateRequest) = poemsApi.updateUser(request)

    suspend fun updateToken(token: String) = poemsApi.uploadMessagingToken(token)

    suspend fun updateImageUrl(url: String) = poemsApi.uploadImageUrl(url)

    suspend fun updatePassword(request: UpdatePasswordRequest) = poemsApi.updatePassword(request)

    suspend fun getUsers() = poemsApi.getUsers()

}