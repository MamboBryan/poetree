package com.mambo.poetree.data.local.entity

import com.mambo.poetree.data.domain.Poem
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Thu 16 Feb 2023
 */
open class DraftRealm(
    @PrimaryKey
    var id: String = RealmUUID.random().toString(),
    var title: String = "",
    var content: String = "",
    var topic: String? = null
) : RealmObject {

    constructor() : this(id = RealmUUID.random().toString())

    fun toPoem() = Poem(
        id = id,
        title = title,
        content = content,
        createdAt = Instant.toString(),
        updatedAt = Instant.toString(),
        topic = Json.decodeFromString(topic ?: ""),
        type = Poem.Type.DRAFT
    )

}
