package com.example.taskmanager

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

open class Task : RealmObject {
    @PrimaryKey
    var id: RealmUUID = RealmUUID.random()
    var imageId: Int = 0
    var content: String = ""
}