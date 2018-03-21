package com.winhtaikaung.devweekly.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.winhtaikaung.devweekly.repository.data.Article
import io.reactivex.Single

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE issueId = (:issueId) limit (:limit) offset (:offset)")
    fun getArticles(limit: Int, offset: Int, issueId: String): Single<List<Article>>

    @Query("SELECT * FROM articles WHERE objectId = (:articleId)")
    fun getArticle(articleId: String): Single<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulkArticle(articleList: List<Article>)
}

