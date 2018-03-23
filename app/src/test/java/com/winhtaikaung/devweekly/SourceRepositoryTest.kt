package com.winhtaikaung.devweekly

import com.nhaarman.mockito_kotlin.mock
import com.winhtaikaung.devweekly.repository.SourceRepository
import com.winhtaikaung.devweekly.repository.api.SourceApi
import com.winhtaikaung.devweekly.repository.data.Data
import com.winhtaikaung.devweekly.repository.data.Source
import com.winhtaikaung.devweekly.repository.data.SourceListResponse
import com.winhtaikaung.devweekly.repository.data.Sources
import com.winhtaikaung.devweekly.repository.db.SourceDao
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class SourceRepositoryTest {
    lateinit var sourceRepository: SourceRepository
    lateinit var sourceApi: SourceApi
    lateinit var sourceDao: SourceDao
    val graphql = "{\n" +
            "  sources(limit: " + 1 + ", page: " + 1 + ") {\n" +
            "    meta {\n" +
            "      totalPages\n" +
            "      current\n" +
            "      prevPage\n" +
            "      nextPage\n" +
            "    }\n" +
            "    data {\n" +
            "      id\n" +
            "      objectId\n" +
            "      tag\n" +
            "      img\n" +
            "      name\n" +
            "      baseUrl\n" +
            "      createdDate\n" +
            "      updatedDate\n" +
            "    }\n" +
            "  }\n" +
            "}\n"

    @Before
    fun setup() {
        sourceApi = mock()
        sourceDao = mock()
        `when`(sourceDao.getSources(1, 1))
                .thenReturn(Single.just(emptyList()))
        sourceRepository = SourceRepository(sourceApi, sourceDao)


    }

    @Test
    fun test_emptyCache_OnSourceListApi_resutEmptyList() {

        `when`(sourceApi.getSourceList(graphql)).thenReturn(Observable.just(SourceListResponse(
                data = Data(source = null, sources = Sources(meta = null, data = emptyList<Source>()), article = null,
                        articles = null,
                        issue = null, issues = null)
        )))
        sourceRepository.getSourceList(1, 1).test()
                .assertValue { it.isEmpty() }
    }
}