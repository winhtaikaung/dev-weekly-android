package com.winhtaikaung.devweekly.repository.api

import com.winhtaikaung.devweekly.repository.data.ArticleResponse
import com.winhtaikaung.devweekly.repository.data.IssueResponse
import com.winhtaikaung.devweekly.repository.data.SourceResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface SourceApi {
    @GET("Graphql Here")
    fun getSource(): Observable<SourceResponse>
}

interface IssueApi {

    @GET("")
    fun getIssue(): Observable<IssueResponse>

}

interface ArticleApi {

    @GET("")
    fun getArticle(): Observable<ArticleResponse>

}