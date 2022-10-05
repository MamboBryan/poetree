package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.local.PoetreeDatabase
import com.mambo.poetree.data.local.entity.TopicEntity
import com.mambo.poetree.data.remote.PoemsApi
import com.mambo.poetree.data.remote.TopicRequest
import kotlinx.coroutines.flow.asFlow

class TopicsRepository() {

    private val poemsApi = PoemsApi()

    private val realm = PoetreeDatabase().realm

    fun topics() = realm.query(clazz = TopicEntity::class).find().map { it.toTopic() }.asFlow()

    suspend fun create(request: TopicRequest) = poemsApi.createTopic(request)

    suspend fun update(topicId: Int, request: TopicRequest) = poemsApi.updateTopic(topicId, request)

    suspend fun delete(topicId: Int) = poemsApi.deleteTopic(topicId)

    suspend fun deleteAll() = realm.write {
        val items = query(clazz = TopicEntity::class).find()
        delete(items)
    }

    suspend fun get(topicId: Int) = poemsApi.getTopic(topicId)

}