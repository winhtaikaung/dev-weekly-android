package com.winhtaikaung.devweekly

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.*
import com.winhtaikaung.devweekly.repository.db.ArticleDao
import com.winhtaikaung.devweekly.repository.db.offsetManager
import com.winhtaikaung.devweekly.util.InstantAppExecutors
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class ArticleRepositoryTest {
    private val countryDao = Mockito.mock(ArticleDao::class.java)
    private val countryService = Mockito.mock(ArticleApi::class.java)
    private val factory = Mockito.mock(DataSource.Factory::class.java)

    private val repo = ArticleRepository(InstantAppExecutors(), countryDao, countryService)
    var articleListGraphql = "{\n" +
            "  articles(limit: " + 1 + ", page: " + 1 + ",issueId:\"61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF\") {\n" +
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

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun loadCountries() {
//        `when`(countryDao.getArticles("123456")).thenReturn(factory.create())
//        repo.loadArticleList("123456")
//        verify(countryDao).getArticles("123456")
    }



}