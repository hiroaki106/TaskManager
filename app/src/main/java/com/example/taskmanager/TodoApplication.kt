package com.example.taskmanager

import android.app.Application
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class TodoApplication : Application() {
    lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        val config = RealmConfiguration.Builder(schema = setOf(Task::class))
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.open(config)
    }
}