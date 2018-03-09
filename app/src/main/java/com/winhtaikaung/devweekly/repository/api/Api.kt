package com.winhtaikaung.devweekly.repository.api

import com.winhtaikaung.devweekly.repository.data.SourceResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface SourceApi{
    @GET("Graphql Here")
    fun getSource(): Observable<SourceResponse>
}