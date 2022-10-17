package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.remote.AuthRequest
import com.mambo.poetree.data.remote.PoemsApi

class AuthRepository {

    private var poemsApi: PoemsApi = PoemsApi()

    suspend fun signIn(email: String, password: String) =
        poemsApi.signIn(AuthRequest(email, password))

    suspend fun signUp(email: String, password: String) =
        poemsApi.signUp(AuthRequest(email, password))

    suspend fun reset(email: String) = poemsApi.reset(email)

    suspend fun refreshToken(token: String) = poemsApi.refreshToken(token)

}