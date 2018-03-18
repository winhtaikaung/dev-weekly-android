package com.winhtaikaung.devweekly

import com.nhaarman.mockito_kotlin.mock
import com.winhtaikaung.devweekly.repository.ArticleRepository
import com.winhtaikaung.devweekly.repository.api.ArticleApi
import org.junit.Before
import org.junit.Test

class ArticleRepositoryTest {
    lateinit var articleRepository: ArticleRepository
    lateinit var articleApi: ArticleApi

    @Before
    fun setup() {
        articleApi = mock()
        articleRepository = ArticleRepository(articleApi)


    }

    @Test
    fun test_emptyCache_OnArticleListApi_resutEmptyList() {


    }
}