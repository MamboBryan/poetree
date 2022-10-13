package com.mambo.poetree.data.repositories

import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.local.PoetreeDatabase
import com.mambo.poetree.data.local.entity.Bookmarked
import com.mambo.poetree.data.local.entity.Drafted
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.CreatePoemRequest
import com.mambo.poetree.data.remote.EditPoemRequest
import com.mambo.poetree.data.remote.PoemRequest
import com.mambo.poetree.data.remote.PoemsApi
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import kotlinx.coroutines.flow.Flow

class PoemRepository {

    private val poemsApi: PoemsApi = PoemsApi()

    private val realm = PoetreeDatabase.realm()

    /**
     * DRAFTS
     */

    suspend fun getDrafts() = realm.write { query(clazz = Drafted::class).find() }.asFlow()

    suspend fun saveDraft(poem: Drafted) {
        realm.write {
            copyToRealm(poem)
        }
    }

    suspend fun updateDraft(poem: Drafted) {
        realm.write {
            val item = query<Drafted>(clazz = Drafted::class, "id == ${poem.id}").first().find()
            item?.apply {
                title = poem.title
                content = poem.content
                html = poem.html
                topic = poem.topic
            }
        }
    }

    suspend fun getDraft(poem: Poem): Flow<ObjectChange<Drafted>> {
        return realm.write {
            query<Drafted>(clazz = Drafted::class, "id == ${poem.id}").find().first()
        }.asFlow()
    }

    suspend fun deleteDraft(poem: Drafted) {
        realm.write {
            val item = query<Drafted>(clazz = Drafted::class, "id == ${poem.id}").find().first()
            delete(item)
        }
    }

    suspend fun deleteDrafts() {
        realm.write {
            val drafts = query(clazz = Drafted::class).find()
            delete(drafts)
        }
    }

    /**
     * BOOKMARKS
     */

    suspend fun getBookmarks() = realm.write { query(clazz = Bookmarked::class).find() }.asFlow()

    suspend fun saveBookmark(poem: Bookmarked) {
        realm.write {
            copyToRealm(poem)
        }
    }

    suspend fun updateBookmark(poem: Bookmarked) {
        realm.write {
            val item =
                query<Bookmarked>(clazz = Bookmarked::class, "id == ${poem.id}").first().find()
            item?.apply {
                title = poem.title
                content = poem.content
                html = poem.html
                topic = poem.topic
                updatedAt = poem.updatedAt
                topic = poem.topic
                reads = poem.reads
                read = poem.read
                bookmarks = poem.bookmarks
                bookmarked = poem.bookmarked
                likes = poem.likes
                liked = poem.liked
                comments = poem.comments
                commented = poem.commented
            }
        }
    }

    suspend fun getBookmark(poem: Bookmarked): Flow<ObjectChange<Bookmarked>> {
        return realm.write {
            query<Bookmarked>(clazz = Bookmarked::class, "id == ${poem.id}").find().first()
        }.asFlow()
    }

    suspend fun deleteBookmark(poem: Bookmarked) {
        realm.write {
            val item =
                query<Bookmarked>(clazz = Bookmarked::class, "id == ${poem.id}").find().first()
            delete(item)
        }
    }

    suspend fun deleteBookmarks() {
        realm.write {
            val drafts = query(clazz = Bookmarked::class).find()
            delete(drafts)
        }
    }

    /**
     * REMOTE
     */

    suspend fun createPublished(request: CreatePoemRequest) = poemsApi.createPoem(request)

    suspend fun updatePublished(request: EditPoemRequest) = poemsApi.updatePoem(request)

    suspend fun deletePublished(poemId: String) = poemsApi.deletePoem(poemId = poemId)

    suspend fun getPublished(poemId: String) = poemsApi.getPoem(PoemRequest(poemId))

    suspend fun markAsRead(poemId: String) = poemsApi.markPoemAsRead(poemId)

    suspend fun bookmark(poemId: String) = poemsApi.bookmarkPoem(poemId = poemId)

    suspend fun unBookmark(poemId: String) = poemsApi.unBookmarkPoem(poemId = poemId)

    // TODO: change getting remote poems to paged data and flow
    suspend fun getPoems(page: Int) = poemsApi.getPoems(page = page)

    // TODO: change getting searched poems to paged data and flow
    suspend fun searchPoems(topicId: Int?, query: String = "", page: Int = 1) =
        poemsApi.searchPoems(topicId = topicId, query = query, page = page)

    // TODO: change getting current logged in user poems to paged data and flow
    suspend fun getMyPoems() {
        val id = UserPreferences().getUserData()?.id ?: ""
        poemsApi.getUser(userId = id)
    }

    // TODO: update getting user poems to paged data and flow
    suspend fun getUserPoems(userId: String, page: Int = 1) =
        poemsApi.getUserPoems(userId = userId, page = page)

}
