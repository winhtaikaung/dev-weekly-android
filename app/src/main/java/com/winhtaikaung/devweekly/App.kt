package com.winhtaikaung.devweekly

import android.app.Application
import android.arch.persistence.room.Room
import com.winhtaikaung.devweekly.article.ArticleDetailViewModel
import com.winhtaikaung.devweekly.article.ArticleListViewModel
import com.winhtaikaung.devweekly.issue.IssueListViewModel
import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.repository.IssueRepository
import com.winhtaikaung.devweekly.repository.SourceRepository
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.api.IssueApi
import com.winhtaikaung.devweekly.repository.api.SourceApi
import com.winhtaikaung.devweekly.repository.db.AppDatabase
import com.winhtaikaung.devweekly.source.SourceListViewModel
import io.reactivex.disposables.Disposable
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
        private lateinit var issueListViewModel: IssueListViewModel
        private lateinit var sourceListviewModel: SourceListViewModel
        private lateinit var articleListviewModel: ArticleListViewModel

        private lateinit var articleDetailViewModel: ArticleDetailViewModel

        private lateinit var appDatabase: AppDatabase
        private lateinit var issueRepository: IssueRepository
        private lateinit var sourceRepository: SourceRepository
        private lateinit var articleRepository: ArticleRepository



    }

    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        // TODO Dependencies Injection should be replaced with existing code
        retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://192.168.0.108:5000/")
                .build()

        sourceApi = retrofit.create(SourceApi::class.java)
        issueApi = retrofit.create(IssueApi::class.java)
        articleApi = retrofit.create(ArticleApi::class.java)

        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "dev-weekly-database").build()

        issueRepository = IssueRepository(issueApi, appDatabase.issueDao())
        issueListViewModel = IssueListViewModel(issueRepository)

        sourceRepository = SourceRepository(sourceApi, appDatabase.sourceDao())
        sourceListviewModel = SourceListViewModel(sourceRepository)

        articleRepository = ArticleRepository(articleApi, appDatabase.articleDao())
        articleListviewModel = ArticleListViewModel(articleRepository)

        articleDetailViewModel = ArticleDetailViewModel(articleRepository)


    }


}

