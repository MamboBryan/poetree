package com.mambo.data.tables.poem

import com.mambo.data.tables.user.UserEntity
import com.mambo.data.tables.user.UsersTable
import io.github.aakira.napier.Napier
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object PoemLikesTable : UUIDTable(name="poems_likes") {

    val createdAt = datetime("created_at")
    val poemId = reference("poem_id", PoemsTable, onDelete = ReferenceOption.CASCADE)
    val userId = reference("user_id", UsersTable, onDelete = ReferenceOption.CASCADE)

}

class PoemLikeEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<PoemLikeEntity>(PoemLikesTable)

    var createdAt by PoemLikesTable.createdAt
    var poem by PoemEntity referencedOn PoemLikesTable.poemId
    var user by UserEntity referencedOn PoemLikesTable.userId

}

data class PoemLike(
    val id: UUID, val createdAt: LocalDateTime, val poemId: UUID, val userId: UUID
)

internal fun ResultRow?.toPoemLike(): PoemLike? {
    if (this == null) return null
    return try {
        PoemLike(
            id = this[PoemLikesTable.id].value,
            createdAt = this[PoemLikesTable.createdAt],
            userId = this[PoemLikesTable.userId].value,
            poemId = this[PoemLikesTable.poemId].value,
        )
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}




