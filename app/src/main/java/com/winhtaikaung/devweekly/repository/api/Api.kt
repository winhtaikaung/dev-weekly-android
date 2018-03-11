package com.winhtaikaung.devweekly.repository.api

import com.winhtaikaung.devweekly.repository.data.ArticleResponse
import com.winhtaikaung.devweekly.repository.data.IssueResponse
import com.winhtaikaung.devweekly.repository.data.SourceResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceApi {
    @GET("api")
    fun getSource(@Query("query") graphqlQuery: String): Observable<SourceResponse>
}

interface IssueApi {

    @GET("api")
    fun getIssue(@Query("query") graphqlQuery: String): Observable<IssueResponse>

}

interface ArticleApi {

    @GET("api")
    fun getArticle(@Query("query") graphqlQuery: String): Observable<ArticleResponse>

}