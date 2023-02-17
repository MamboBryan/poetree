package com.mambo.poetree.data.local.entity

import com.mambo.poetree.data.domain.Topic
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class TopicRealm(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var color: String = "",
    var createdAt: String = "",
    var updatedAt: String? = null
) : RealmObject {

    constructor() : this(id = 0)

    fun toTopic() = Topic(
        id = id,
        name = name,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

}