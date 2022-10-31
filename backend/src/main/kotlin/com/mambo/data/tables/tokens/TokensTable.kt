package com.mambo.data.tables.tokens

import com.mambo.data.tables.user.UsersTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object TokensTable : UUIDTable() {
    val user = reference("user", UsersTable, onDelete = ReferenceOption.CASCADE)
    val token = varchar("token", 512)
    val expiration = varchar("expiration", 255)
}