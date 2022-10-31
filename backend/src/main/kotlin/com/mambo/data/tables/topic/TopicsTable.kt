package com.mambo.data.tables.topic

import com.mambo.application.utils.toDate
import com.mambo.application.utils.toDateTimeString
import io.github.aakira.napier.Napier
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TopicsTable : IntIdTable(name = "topics") {

    val createdAt = datetime("topic_created_at")
    val updatedAt = datetime("topic_updated_at").nullable()
    val name = varchar("topic_name", 500).uniqueIndex()
    val color = varchar("topic_color", 500).uniqueIndex()

}

class TopicEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<TopicEntity>(TopicsTable)

    var createdAt by TopicsTable.createdAt
    var updatedAt by TopicsTable.updatedAt
    var name by TopicsTable.name
    var color by TopicsTable.color

}

data class Topic(
    val id: Int,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val name: String,
    val color: String,
)

data class TopicDto(
    val id: Int,
    val createdAt: String?,
    val updatedAt: String?,
    val name: String,
    val color: String,
)

internal fun Topic?.toTopicDto(): TopicDto? {
    if (this == null) return null
    return try {
        TopicDto(
            id = this.id,
            createdAt = this.createdAt.toDate().toDateTimeString(),
            updatedAt = this.createdAt.toDate().toDateTimeString(),
            name = this.name,
            color = this.color
        )
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

internal fun Query?.toTopicList(): List<Topic?> {
    if (this == null || this.empty()) return emptyList()
    return this.map { it.toTopic() }
}

internal fun ResultRow?.toTopic(): Topic? {
    if (this == null) return null
    return Topic(
        id = this[TopicsTable.id].value,
        createdAt = this[TopicsTable.createdAt],
        updatedAt = this[TopicsTable.updatedAt],
        name = this[TopicsTable.name],
        color = this[TopicsTable.color],
    )
}

internal fun TopicEntity?.toTopic(): Topic? {
    if (this == null) return null
    return try {
        Topic(
            id = this.id.value,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            name = this.name,
            color = this.color
        )
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}