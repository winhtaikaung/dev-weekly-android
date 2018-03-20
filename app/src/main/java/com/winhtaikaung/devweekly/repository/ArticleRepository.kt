package com.winhtaikaung.devweekly.repository

import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.Article
import io.reactivex.Observable

class ArticleRepository(val articleApi: ArticleApi) {
    fun getArticleListFromApi(limit: Int, page: Int): Observable<List<Article>> {
        val graphql = "{\n" +
                "  articles(limit: " + limit + ", page: " + page + ") {\n" +
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
        return articleApi.getArticleList(graphql).flatMap { (data) -> Observable.just(data.articles!!.data!!) }
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
        return articleApi.getArticle(graphql).flatMap { (data) -> Observable.just(data.article!!) }
    }
}