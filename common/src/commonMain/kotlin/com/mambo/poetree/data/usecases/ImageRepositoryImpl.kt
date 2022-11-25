package com.mambo.poetree.data.usecases

import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.NetworkResult
import com.mambo.poetree.data.remote.PoemsApi
import com.mambo.poetree.data.repositories.ImageRepository
import io.ktor.client.request.forms.*

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Fri 25 Nov 2022
 */
class ImageRepositoryImpl(
    private val api: PoemsApi = PoemsApi(),
    private val preferences: UserPreferences = UserPreferences()
) : ImageRepository {

    override suspend fun upload(form: MultiPartFormDataContent): NetworkResult<String> {
        return api.uploadImage(form)
    }

    override suspend fun delete(): NetworkResult<Boolean> {
        return api.deleteImage(preferences.getUserData()?.id ?: "")
    }
}