package com.mambo.data

import com.mambo.data.tables.comment.CommentLikesTable
import com.mambo.data.tables.comment.CommentsTable
import com.mambo.data.tables.poem.BookmarksTable
import com.mambo.data.tables.poem.PoemLikesTable
import com.mambo.data.tables.poem.PoemsTable
import com.mambo.data.tables.poem.ReadsTable
import com.mambo.data.tables.tokens.TokensTable
import com.mambo.data.tables.topic.TopicsTable
import com.mambo.data.tables.user.UsersTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.transactionScope
import javax.sql.DataSource

object DatabaseFactory {

    fun init() {

        connectAndMigrate()
        createDatabaseAndTables()

    }

    private fun connectAndMigrate() {
        val hikari = getHikariDatasource()
        Database.connect(datasource = hikari)
        connectFlyaway(datasource = hikari)
    }

    private fun createDatabaseAndTables() {

//        IN CASE I WANT TO NUKE EVERYTHING
//        transaction{
//            // delete tables
//            SchemaUtils.drop(
//                UsersTable,
//                TopicsTable,
//                CommentsTable,
//                CommentLikesTable,
//                PoemsTable,
//                PoemLikesTable,
//                ReadsTable,
//                BookmarksTable,
//                TokensTable,
//            )
//        }
//
//        transactionScope {
//            SchemaUtils.dropDatabase("poetree")
//        }

        transactionScope {
            SchemaUtils.createDatabase("poetree")
        }

        transaction {

            // create tables
            SchemaUtils.create(
                UsersTable,
                TopicsTable,
                CommentsTable,
                CommentLikesTable,
                PoemsTable,
                PoemLikesTable,
                ReadsTable,
                BookmarksTable,
                TokensTable,
                inBatch = true
            )

        }
    }

    private fun connectFlyaway(datasource: DataSource) {
        val flyway = Flyway.configure().dataSource(datasource).baselineOnMigrate(true).load()
        flyway.migrate()
    }

    private fun getHikariDatasource(): HikariDataSource {
        val config = HikariConfig().apply {
            password = System.getenv("JDBC_PASSWORD")
            driverClassName = System.getenv("JDBC_DRIVER_CLASS")
            jdbcUrl = System.getenv("JDBC_DATABASE_URL")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

}
