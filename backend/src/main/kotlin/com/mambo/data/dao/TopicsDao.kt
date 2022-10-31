package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.requests.TopicRequest
import com.mambo.data.tables.topic.TopicsTable
import com.mambo.data.tables.topic.toTopic
import com.mambo.data.tables.topic.toTopicDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDateTime
import java.util.UUID

class TopicsDao {

    suspend fun create(request: TopicRequest): ServerResponse<out Any?> = safeTransaction(
        error = "failed while creating topic"
    ) {

        if (request.name.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse(message = "invalid name")
        if (request.color.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse(message = "invalid color")
        if (!request.color.isValidHexColor()) return@safeTransaction defaultBadRequestResponse(message = "invalid color")

        val condition =
            Op.build { TopicsTable.name like "%${request.name}%" or (TopicsTable.color like "%${request.color}%") }
        val exists = query { TopicsTable.select(condition).empty().not() }

        if (exists) return@safeTransaction defaultBadRequestResponse(message = "topic name or color already exists")

        var statement: InsertStatement<Number>? = null

        query {
            statement = TopicsTable.insert {
                it[createdAt] = LocalDateTime.now()
                it[name] = request.name
                it[color] = request.color
            }
        }

        val data = statement?.resultedValues?.get(0).toTopic().toTopicDto()

        defaultCreatedResponse(message = "topic created successfully", data = data)

    }

    suspend fun get(topicId: Int) = safeTransaction(
        error = "failed getting topic"
    ) {
        query {

            val result =
                TopicsTable.select { TopicsTable.id eq topicId }.firstOrNull() ?: return@query defaultNotFoundResponse(
                    message = "Topic not found"
                )

            val data = result.toTopic().toTopicDto()

            defaultOkResponse(message = "topic", data = data)

        }
    }

    suspend fun getTopics(page: Int) = safeTransaction(
        error = "failed getting topic list"
    ) {
        query {

            val (limit, offset) = getLimitAndOffset(page)

            val list =
                TopicsTable.selectAll().limit(n = limit, offset = offset).sortedByDescending { TopicsTable.updatedAt }
                    .map { it.toTopic().toTopicDto() }

            val data = getPagedData(page = page, result = list)

            defaultOkResponse(message = "topics", data = data)

        }
    }

    suspend fun update(topicId: Int, request: TopicRequest) = safeTransaction(
        error = "failed updating topic"
    ) {
        query {


            if (request.name.isNullOrBlank() and request.color.isNullOrBlank()) return@query defaultBadRequestResponse("topic name and color cannot be blank")

            val topicExists = TopicsTable.select(TopicsTable.id eq topicId).empty().not()
            if (!topicExists) return@query defaultNotFoundResponse(message = "topic not found")

            val existsCondition =
                Op.build { TopicsTable.name like "%${request.name}%" or (TopicsTable.color like "%${request.color}%") }
            val exists = TopicsTable.select(existsCondition).empty().not()

            if (exists) return@query defaultBadRequestResponse(message = "topic name or color already exists")

            val condition = Op.build { TopicsTable.id eq topicId }

            TopicsTable.update({ condition }) {
                it[updatedAt] = LocalDateTime.now()
                if (!request.name.isNullOrBlank()) it[name] = request.name
                if (!request.color.isNullOrBlank() && request.color.isValidHexColor()) it[color] = request.color
            }

            val topic = TopicsTable.select(TopicsTable.id eq topicId).map { it.toTopic().toTopicDto() }.singleOrNull()
            defaultOkResponse(message = "topic updated successfully", data = topic)

        }
    }

    suspend fun delete(topicId: Int) = safeTransaction(
        error = "failed deleting topic"
    ) {
        query {

            val topicExists = TopicsTable.select(TopicsTable.id eq topicId).empty().not()
            if (!topicExists) return@query defaultNotFoundResponse(message = "topic not found")

            val result = TopicsTable.deleteWhere { TopicsTable.id eq topicId }
            defaultOkResponse(message = "topic deleted successfully", data = result != 0)

        }
    }

}