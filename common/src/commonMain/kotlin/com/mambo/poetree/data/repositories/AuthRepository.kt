package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.AuthRequest
import com.mambo.poetree.data.remote.NetworkResult
import com.mambo.poetree.data.remote.PoemsApi

class AuthRepository {

    private var poemsApi: PoemsApi = PoemsApi()
    private val preferences: UserPreferences = UserPreferences()

    suspend fun signIn(email: String, password: String): NetworkResult<Boolean> {
        val response = poemsApi.signIn(AuthRequest(email, password))
        if (response.isSuccessful.not()) {
            return NetworkResult(false, message = response.message, data = false)
        }
        val data = response.data

        data?.let {
            preferences.updateTokens(
                access = it.token.accessToken,
                refresh = it.token.refreshToken
            )
            preferences.saveUserDetails(details = it.user.toUser())
        }

        preferences.signedIn(bool = true)

        return NetworkResult(
            isSuccessful = true,
            message = response.message,
            data = data?.user?.isSetup
        )

    }

    suspend fun signUp(email: String, password: String): NetworkResult<Boolean> {
        val response = poemsApi.signUp(AuthRequest(email, password))
        if (response.isSuccessful.not()) {
            return NetworkResult(false, message = response.message, data = false)
        }
        val data = response.data

        data?.let {
            preferences.updateTokens(
                access = it.token.accessToken,
                refresh = it.token.refreshToken
            )
            preferences.saveUserDetails(details = it.user.toUser())
        }

        preferences.signedIn(bool = true)

        return NetworkResult(
            isSuccessful = true,
            message = response.message,
            data = false
        )

    }

    suspend fun reset(email: String) = poemsApi.reset(email)

    suspend fun refreshToken(token: String) = poemsApi.refreshToken(token)

}