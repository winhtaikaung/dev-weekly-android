package com.winhtaikaung.devweekly.repository.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.data.Issue
import com.winhtaikaung.devweekly.repository.data.Source

@Database(entities = arrayOf(Source::class, Issue::class, Article::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sourceDao(): SourceDao
    abstract fun issueDao(): IssueDao
    abstract fun articleDao(): ArticleDao
}

fun offsetManager(page: Int, limit: Int): Int {
    var p = page
    if (p == 0) {
        p = 1
    }
    return (p * limit) - limit
}