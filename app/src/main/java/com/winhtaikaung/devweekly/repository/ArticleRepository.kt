package com.winhtaikaung.devweekly.repository

import android.util.Log
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.Article
import com.winhtaikaung.devweekly.repository.db.ArticleDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class ArticleRepository(val articleApi: ArticleApi, val articleDao: ArticleDao) {

    fun getArticleList(limit: Int, page: Int, issueId: String): Observable<List<Article>> {
        return Observable.concatArray(
                getArticleListFromApi(limit, page, issueId),
                getArticleListFromDB(limit, page, issueId))
    }

    fun getArticleListFromApi(limit: Int, page: Int, issueId: String): Observable<List<Article>> {
        val graphql = "{\n" +
                "  articles(limit: " + limit + ", page: " + page + ",issueId:\"" + issueId + "\") {\n" +
                "    meta {\n" +
                "      totalPages\n" +
                "      current\n" +
                "      prevPage\n" +
                "      nextPage\n" +
                "    }\n" +
                "    data {\n" +
                "      id\n" +
                "      objectId\n" +
                "      url\n" +
                "      img\n" +
                "      mainUrl\n" +
                "      title\n" +
                "      preContent\n" +
                "      issueId\n" +
                "      sourceId\n" +
                "      createdDate\n" +
                "      updatedDate\n" +
                "    }\n" +
                "  }\n" +
                "}\n"
        return articleApi.getArticleList(graphql).flatMap { (data) ->
            storesArticleListinDB(data.articles!!.data!!)
            Observable.just(data.articles!!.data!!)

        }.onErrorReturn {
                    emptyList()
                }
    }

    fun getArticleListFromDB(limit: Int, page: Int, issueId: String): Observable<List<Article>> {
        return articleDao.getArticles(limit, page, issueId).filter { it.isNotEmpty() }
                .toObservable()
                .doOnNext {
                    Log.e("DAO", "Dispatching ${it.size} articles from DB...")

                }
    }


    fun getArticle(articleId: String): Observable<Article> {
        return Observable.concat(getArticleFromDB(articleId), getArticleFromApi(articleId))
    }

    fun getArticleFromApi(articleId: String): Observable<Article> {
        val graphql = "{\n" +
                "  article(articleId:\"" + articleId + "\"){\n" +
                "    id\n" +
                "    objectId\n" +
                "    url\n" +
                "    img\n" +
                "    mainUrl\n" +
                "    title\n" +
                "    preContent\n" +
                "    sourceId\n" +
                "    issueId\n" +
                "    createdDate\n" +
                "    updatedDate\n" +
                "  }\n" +
                "}"
        return articleApi.getArticle(graphql).flatMap { (data) ->
            storeArticleinDB(data.article!!)
            Observable.just(data.article!!)
        }
    }

    fun getArticleFromDB(articleId: String): Observable<Article> {
        return articleDao.getArticle(articleId)
                .toObservable()
                .doOnNext {
                    //                    Log.e("DAO", "Dispatching ${it.toString()} article from DB")
                }
    }

    fun storeArticleinDB(article: Article) {
        Observable.fromCallable { articleDao.insertArticle(article) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    //                    Log.e("DAO", "Saving ${article.toString()} into DB")
                }
    }

    fun storesArticleListinDB(articles: List<Article>) {
        Observable.fromCallable { articleDao.insertBulkArticle(articles) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    //                    Log.e("DAO", "Saving ${articles.size} articles into DB...")
                }
    }
}