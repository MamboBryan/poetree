package com.mambo.poetree.data.domain

import com.mambo.poetree.data.local.entity.Drafted

open class Poem {

    open var id: String = ""
    open var title: String = ""
    open var content: String = ""
    open var html: String = ""
    open var createdAt: String = ""
    open var updatedAt: String = ""
    open var topic: Topic? = null
    open var type: Type = Type.REMOTE
    open var user: User? = null
    open var reads: Long = 0
    open var read: Boolean = false
    open var bookmarks: Long = 0
    open var bookmarked: Boolean = false
    open var likes: Long = 0L
    open var liked: Boolean = false
    open var comments: Long = 0L
    open var commented: Boolean = false
    open var remoteId: String? = null

    enum class Type {
        LOCAL,
        BOOKMARK,
        REMOTE
    }

    fun isLocal(): Boolean = user == null

    fun isMine(userId: String?): Boolean = user?.id == userId

    fun copy(
        mTitle: String? = null,
        mContent: String? = null,
        mHtml: String? = null,
        mCreatedAt: String? = null,
        mUpdatedAt: String? = null,
        mTopic: Topic? = null,
    ): Poem {
        return Poem().apply {
            id = this.id
            title = mTitle.takeIf { it != null } ?: this.title
            content = mContent.takeIf { it != null } ?: this.content
            html = mHtml.takeIf { it != null } ?: this.html
            createdAt = mCreatedAt.takeIf { it != null } ?: this.createdAt
            updatedAt = mUpdatedAt.takeIf { it != null } ?: this.updatedAt
            topic = mTopic.takeIf { it != null } ?: this.topic
        }
    }

    fun toDraft(): Drafted = Drafted().apply {
        id = this@Poem.id
        title = this@Poem.title
        content = this@Poem.content
        html = this@Poem.html
        createdAt = this@Poem.createdAt
        updatedAt = this@Poem.updatedAt
        topic = this@Poem.topic
    }

}



