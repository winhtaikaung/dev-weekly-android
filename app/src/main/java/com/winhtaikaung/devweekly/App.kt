package com.winhtaikaung.devweekly

import android.app.Application
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.api.IssueApi
import com.winhtaikaung.devweekly.repository.api.SourceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var sourceApi: SourceApi
        private lateinit var issueApi: IssueApi
        private lateinit var articleApi: ArticleApi

    }

    override fun onCreate() {
        super.onCreate()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://devweekly.winhtaikaung.com/api/")
                .build()

        sourceApi = retrofit.create(SourceApi::class.java)
        issueApi = retrofit.create(IssueApi::class.java)
        articleApi = retrofit.create(ArticleApi::class.java)
    }
}
