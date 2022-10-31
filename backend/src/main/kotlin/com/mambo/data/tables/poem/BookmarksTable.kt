package com.mambo.data.tables.poem

import com.mambo.data.tables.user.UserEntity
import com.mambo.data.tables.user.UsersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object BookmarksTable : UUIDTable(name="bookmarks") {

    val createdAt = datetime("created_at")
    val poemId = reference("poem_id", PoemsTable, onDelete = ReferenceOption.CASCADE)
    val userId = reference("user_id", UsersTable, onDelete = ReferenceOption.CASCADE)

}

class BookmarkEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<BookmarkEntity>(BookmarksTable)

    var createdAt by BookmarksTable.createdAt
    var poem by PoemEntity referencedOn BookmarksTable.poemId
    var user by UserEntity referencedOn BookmarksTable.userId

}

data class Bookmark(
    val id: UUID, val createdAt: LocalDateTime, val poemId: UUID, val userId: UUID
)

internal fun ResultRow?.toBookmark(): Bookmark? {
    if (this == null) return null
    return Bookmark(
        id = this[BookmarksTable.id].value,
        createdAt = this[BookmarksTable.createdAt],
        userId = this[BookmarksTable.userId].value,
        poemId = this[BookmarksTable.poemId].value,
    )
}
