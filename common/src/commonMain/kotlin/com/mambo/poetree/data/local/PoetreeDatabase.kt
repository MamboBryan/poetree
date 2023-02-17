package com.mambo.poetree.data.local

import com.mambo.poetree.data.local.entity.DraftRealm
import com.mambo.poetree.data.local.entity.TopicRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

internal object PoetreeDatabase {

    private val config = RealmConfiguration.Builder(
        setOf(
            DraftRealm::class,
//            Bookmarked::class,
//            Searched::class,
//            PoemFeed::class,
            TopicRealm::class
        )
    ).build()

    fun realm() = Realm.open(config)

}