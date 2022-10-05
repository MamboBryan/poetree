package com.mambo.poetree.data.local

import com.mambo.poetree.data.local.entity.TopicEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class PoetreeDatabase {

    private val config = RealmConfiguration.Builder(
        schema = setOf(
            TopicEntity::class
        )
    ).build()

    val realm: Realm = Realm.open(config)

}