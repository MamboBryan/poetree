package com.mambo.poetree.data.local.entity

import com.mambo.poetree.data.domain.Poem
import io.realm.kotlin.schema.RealmStorageType
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class Drafted : Poem(), RealmObject {
    @PrimaryKey
    override var id: String = RealmStorageType.UUID.toString()
    override var type: Type = Type.LOCAL
}

class Bookmarked : Poem(), RealmObject {
    @PrimaryKey
    override var id: String = RealmStorageType.UUID.toString()
    override var type: Type = Type.BOOKMARK
}

class Published : Poem(), RealmObject {
    @PrimaryKey
    override var id: String = RealmStorageType.UUID.toString()
    override var type: Type = Type.BOOKMARK
}

class Searched : Poem(), RealmObject {
    @PrimaryKey
    override var id: String = RealmStorageType.UUID.toString()
    override var type: Type = Type.BOOKMARK
}
