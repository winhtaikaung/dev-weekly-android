package com.winhtaikaung.devweekly

import com.nhaarman.mockito_kotlin.mock
import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import com.winhtaikaung.devweekly.repository.data.*
import com.winhtaikaung.devweekly.repository.db.ArticleDao
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class ArticleRepositoryTest {
    lateinit var articleRepository: ArticleRepository
    lateinit var articleApi: ArticleApi
    lateinit var articleDao: ArticleDao
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

    @Before
    fun setup() {
        articleApi = mock()
        articleDao = mock()
        `when`(articleDao.getArticles(1, 1, "61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF"))
                .thenReturn(Single.just(emptyList()))
        articleRepository = ArticleRepository(articleApi, articleDao)


    }

    @Test
    fun test_emptyCache_OnArticleListApi_resutEmptyList() {
        `when`(articleApi.getArticleList(articleListGraphql)).thenReturn(Observable.just(ArticleListResponse(
                data = Data(source = null, sources = null, article = null,
                        articles = Articles(meta = null, data = emptyList<Article>()),
                        issue = null, issues = null)
        )))
        articleRepository.getArticleList(1, 1, "61F1CF54-B775-4EAD-A56E-F8F7F65CEDAF").test()
                .assertValue { it.isEmpty() }

    }

}