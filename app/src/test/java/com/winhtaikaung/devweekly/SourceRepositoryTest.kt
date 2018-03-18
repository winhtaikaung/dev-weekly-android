package com.winhtaikaung.devweekly

import com.nhaarman.mockito_kotlin.mock
import com.winhtaikaung.devweekly.repository.SourceRepository
import com.winhtaikaung.devweekly.repository.api.SourceApi
import com.winhtaikaung.devweekly.repository.data.Data
import com.winhtaikaung.devweekly.repository.data.Source
import com.winhtaikaung.devweekly.repository.data.SourceListResponse
import com.winhtaikaung.devweekly.repository.data.Sources
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class SourceRepositoryTest {
    lateinit var sourceRepository: SourceRepository
    lateinit var sourceApi: SourceApi

    @Before
    fun setup() {
        sourceApi = mock()
        sourceRepository = SourceRepository(sourceApi)


    }

    @Test
    fun test_emptyCache_OnSourceListApi_resutEmptyList() {


    }
}