package com.winhtaikaung.devweekly.repository

import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.winhtaikaung.devweekly.AppExecutors
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.db.ArticleDao
import com.winhtaikaung.devweekly.repository.db.offsetManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val appExecutor: AppExecutors, val articleDao: ArticleDao, val articleApi: ArticleApi) {

   fun loadArticleList() {}
}