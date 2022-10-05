package com.mambo.poetree.data.local.entity

import com.mambo.poetree.data.domain.Topic
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class TopicEntity : RealmObject {

    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var color: String = ""
    var createdAt: String = ""
    var updatedAt: String? = null

    fun toTopic() = Topic(id, name, color, createdAt, updatedAt)

}