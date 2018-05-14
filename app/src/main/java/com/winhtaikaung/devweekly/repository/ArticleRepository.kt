package com.winhtaikaung.devweekly.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.winhtaikaung.devweekly.AppExecutors
import com.winhtaikaung.devweekly.api.ApiResponse
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.data.ArticleListResponse
import com.winhtaikaung.devweekly.repository.db.ArticleDao
import com.winhtaikaung.devweekly.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(private val appExecutor: AppExecutors, val articleDao: ArticleDao, val articleApi: ArticleApi) {

    fun loadArticleList(issuId: String): LiveData<Resource<PagedList<Article>>> {

        var query = ""

        return object : NetworkBoundResource<PagedList<Article>, ArticleListResponse>(appExecutor) {

            override fun createCall(): LiveData<ApiResponse<ArticleListResponse>> = articleApi.getArticleList(query)

            override fun loadFromDb(): LiveData<PagedList<Article>> {
                return LivePagedListBuilder(articleDao.getArticles(issuId), 20).build()
            }

            override fun shouldFetch(data: PagedList<Article>?): Boolean = data == null || data.isEmpty()

            override fun saveCallResult(item: ArticleListResponse) {
                articleDao.insertBulkArticle(item.data.articles.data)
            }
        }.asLiveData()
    }
}