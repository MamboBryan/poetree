package com.mambo.poetree.data.local

import com.mambo.poetree.data.local.entity.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

internal object PoetreeDatabase {

    private val config = RealmConfiguration.Builder(
        setOf(
            Drafted::class,
            Bookmarked::class,
            Searched::class,
            Published::class,
            TopicEntity::class
        )
    ).build()

    fun realm() = Realm.open(config)

}