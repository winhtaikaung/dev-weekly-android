package com.winhtaikaung.devweekly.repository.api

import com.winhtaikaung.devweekly.repository.data.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceApi {
    @GET("api")
    fun getSource(@Query("query") graphqlQuery: String): Observable<SourceResponse>

    @GET("api")
    fun getSourceList(@Query("query") graphqlQuery: String): Observable<SourceListResponse>
}

interface IssueApi {

    @GET("api")
    fun getIssue(@Query("query") graphqlQuery: String): Observable<IssueResponse>

    @GET("api")
    fun getIssueList(@Query("query") graphqlQuery: String): Observable<IssueListResponse>

}

interface ArticleApi {

    @GET("api")
    fun getArticle(@Query("query") graphqlQuery: String): Observable<ArticleResponse>

    @GET("api")
    fun getArticleList(@Query("query") graphqlQuery: String): Observable<ArticleListResponse>

}