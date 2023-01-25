package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.remote.NetworkResult
import io.ktor.client.request.forms.*

/**
 * Poetree
 * @author Mambo Bryan
 * @email mambobryan@gmail.com
 * Created 4/17/22 at 10:43 PM
 */

interface ImageRepository {

    suspend fun upload(form: MultiPartFormDataContent): NetworkResult<String>

    suspend fun delete(): NetworkResult<Boolean>

}