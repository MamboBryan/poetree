package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.remote.PoemsApi

class PoetreeRepository {

    private val api = PoemsApi()

    suspend fun goofy() = api.goofy()


}