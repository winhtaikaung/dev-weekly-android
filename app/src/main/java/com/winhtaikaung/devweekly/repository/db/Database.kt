package com.winhtaikaung.devweekly.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.data.Source

@Database(entities = arrayOf(Source::class, Issue::class, Article::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
}